package org.kryptonmlt.damselbuster;

import org.kryptonmlt.damselbuster.repositories.GameRepository;
import org.kryptonmlt.damselbuster.repositories.PlatformRepository;
import org.kryptonmlt.damselbuster.scraper.IGTGameSearch;
import org.kryptonmlt.damselbuster.scraper.ScheduledTasks;
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
public class DamselRunner implements org.springframework.boot.CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DamselRunner.class);

    @Value("${startType}")
    private String startType;

    @Value("${chromeDriver}")
    private String chromeDriver;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private IGTGameSearch iGTGameSearch;

    @Autowired
    private ScheduledTasks scheduledTasks;

    /**
     * Starts the Game Search Scraper
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        LOGGER.info("Starting Damsel Buster");
        LOGGER.info(startType);
        switch (startType) {
            case "init":
                LOGGER.info("Deleting all database entries");
                gameRepository.deleteAll();
                platformRepository.deleteAll();
                LOGGER.info("Crawling IGT Game Search from scratch");
                iGTGameSearch.scrapeAllGames();
                scheduledTasks.setPerformUpdates(true);
                break;
            case "update":
                LOGGER.info("Updating IGT Game Search once a day");
                scheduledTasks.setPerformUpdates(true);
                break;
            default:
                LOGGER.error("Set JVM argument startType to init if you want to scrape from scratch, otherwise set to update");
        }

    }

}
