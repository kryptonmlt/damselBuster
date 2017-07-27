package org.kryptonmlt.damselbuster.scraper;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import org.kryptonmlt.damselbuster.mappers.GameMapper;
import org.kryptonmlt.damselbuster.pojos.Game;
import org.kryptonmlt.damselbuster.pojos.Platform;
import org.kryptonmlt.damselbuster.repositories.GameRepository;
import org.kryptonmlt.damselbuster.repositories.PlatformRepository;
import org.kryptonmlt.damselbuster.utils.ExtractorUtils;
import org.kryptonmlt.damselbuster.utils.ImageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kurt
 */
@Component
public class IGTGameSearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(IGTGameSearch.class);

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Value("${imagesPath}")
    private String imagesPath;

    private WebDriver driver;

    public IGTGameSearch() {
    }

    /**
     * Sets the driver page to All Games
     */
    private void setToAllGames() {
        LOGGER.debug("Setting game search to All Games");
        Select select = new Select(driver.findElement(By.className("ddl_select_by")));
        changePageToPlatform(select, "All Games");
    }

    /**
     * Scrapes Game Search for all the Games it contains without any breaks.
     * Also saves them in DB
     */
    public void scrapeAllGames() {
        driver = new ChromeDriver();
        driver.get("https://www.igt.com/en/products-and-services/gaming/game-search");
        getAllGames();
        //Close the browser
        driver.quit();
        driver = null;
    }

    /**
     * Scrapes the New Games only and saves to DB
     *
     * @return new game titles only
     */
    public List<String> scrapeNewGames() {
        List<String> addedGames = new ArrayList<>();

        return addedGames;
    }

    /**
     * Retrieves all the games on IGT Game Search including platform name
     *
     * @param driver
     */
    private void getAllGames() {
        setToAllGames();
        Select select = getPlatformSelect();
        List<String> platforms = getSelectOptions(select);
        for (String platform : platforms) {
            LOGGER.debug("Retrieving Games for platform: {}", platform);
            select = getPlatformSelect();
            changePageToPlatform(select, platform);
            getAllGamesInPlatform(platform);
        }
    }

    /**
     * Retrieves Games per platform page, including next pages
     *
     * @param driver
     * @param platform
     */
    private void getAllGamesInPlatform(String platform) {
        int iter = 1;
        do {
            WebElement catalog = driver.findElement(By.className("games_list"));
            List<WebElement> items = catalog.findElements(By.cssSelector(".three.columns"));
            LOGGER.debug("Page: {}, items: {}", iter, items.size());
            for (WebElement item : items) {
                WebElement img = item.findElement(By.tagName("img"));
                String imageUrl = img.getAttribute("src");
                String title = item.findElement(By.className("game-title")).getText();
                String data = item.findElement(By.className("game-data")).getText();
                saveGame(title, data, imageUrl, platform);
            }
            iter++;
        } while (nextContentPage());
    }

    /**
     * Persists the Game to the connected database
     *
     * @param name
     * @param description
     * @param imageUrl
     * @param platform
     */
    private void saveGame(String name, String description, String imageUrl, String platform) {
        LOGGER.info(name + ", " + description + ", " + imageUrl);
        platform = ExtractorUtils.stripWord(platform, "(");
        Platform plat = platformRepository.findByName(platform);
        if (plat == null) {
            Platform temp = new Platform();
            temp.setName(platform);
            platformRepository.save(temp);
            plat = platformRepository.findByName(platform);
        }
        Image image = ImageUtils.retrieveImage(imageUrl);
        Game game = GameMapper.createGame(name, description, "", plat);
        gameRepository.save(game);
        String path = ImageUtils.saveImage(image, imagesPath, game.getId() + ".png");
        game.setImagePath(path);
        gameRepository.save(game);
    }

    /**
     * Changes platform page
     *
     * @param driver
     * @param select
     * @param option
     */
    private void changePageToPlatform(Select select, String option) {
        select.selectByVisibleText(option);
        waitForPageToLoad();
    }

    /**
     * waits the page to load after performing an action
     *
     * @param driver
     */
    private void waitForPageToLoad() {
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException ex) {
            LOGGER.error("Waiting for page to load", ex);
        }
        //style="display: block;"
        //style="display: none;"
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                String value = d.findElement(By.id("ContentPlaceHolder1_main_0_UpdateProgress1")).getCssValue("display");
                return "none".equals(value);
            }
        });
    }

    /**
     * Similar to pressing the next button on the numbered pages bar
     *
     * @param driver
     * @return
     */
    private boolean nextContentPage() {
        WebElement pager = driver.findElement(By.id("ContentPlaceHolder1_main_0_ulDataPagerTop"));
        List<WebElement> pages = pager.findElements(By.tagName("li"));

        //get one before the last
        WebElement next = pages.get(pages.size() - 2);
        WebElement link = next.findElement(By.tagName("a"));
        if ("aspNetDisabled".equals(link.getAttribute("class"))) {
            // no more pages left
            return false;
        }
        link.click();
        waitForPageToLoad();
        return true;
    }

    /**
     * gets the platform selection element
     *
     * @param driver
     * @return
     */
    private Select getPlatformSelect() {
        WebElement platforms = driver.findElement(By.className("ddl_platform"));
        return new Select(platforms);
    }

    /**
     * Gets the options available in a select element
     *
     * @param select
     * @return
     */
    private List<String> getSelectOptions(Select select) {
        List<String> options = new ArrayList<>();
        for (WebElement element : select.getOptions()) {
            if (!"All Platforms".equals(element.getText())) {
                options.add(element.getText());
            }
        }
        return options;
    }
}
