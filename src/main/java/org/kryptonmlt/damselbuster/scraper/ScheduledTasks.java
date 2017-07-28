package org.kryptonmlt.damselbuster.scraper;

import java.util.List;
import org.kryptonmlt.damselbuster.utils.EmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class which stores periodic jobs, namely updating the games list each day
 *
 * @author Kurt
 */
@Component
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    private boolean performUpdates = false;

    @Autowired
    private IGTGameSearch iGTGameSearch;

    @Autowired
    private EmailUtils emailUtils;

    /**
     * A cron job which periodically runs at 1am each day to retrieve the newest
     * games
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void findNewestGames() {
        if (performUpdates) {
            LOGGER.info("Running cronjob: findNewestGames");
            List<String> addedGames = iGTGameSearch.scrapeNewGames();
            if (!addedGames.isEmpty()) {
                emailUtils.sendEmail(addedGames);
            } else {
                LOGGER.info("Running cronjob: No new Games found");
            }
        }
    }

    public boolean isPerformUpdates() {
        return performUpdates;
    }

    public void setPerformUpdates(boolean performUpdates) {
        this.performUpdates = performUpdates;
    }

}
