package org.kryptonmlt.damselbuster.scraper;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private boolean performUpdates = false;

    @Autowired
    private IGTGameSearch iGTGameSearch;

    @Autowired
    private EmailUtils emailUtils;

    /**
     * A cron job which periodically runs at 1am each day to retrieve the newest
     * games
     */
    //@Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(cron = "0 * * * * ?")
    public void findNewestGames() {
        if (performUpdates) {
            LOGGER.info("The time is now {}", dateFormat.format(new Date()));
            List<String> addedGames = iGTGameSearch.scrapeNewGames();
            if (!addedGames.isEmpty()) {
                emailUtils.sendEmail(addedGames);
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
