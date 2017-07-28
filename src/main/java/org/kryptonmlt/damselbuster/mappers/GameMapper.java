package org.kryptonmlt.damselbuster.mappers;

import org.kryptonmlt.damselbuster.enums.InfoTypes;
import org.kryptonmlt.damselbuster.pojos.Game;
import org.kryptonmlt.damselbuster.pojos.Platform;
import org.kryptonmlt.damselbuster.utils.ExtractorUtils;

/**
 *
 * @author Kurt
 */
public class GameMapper {

    private GameMapper() {

    }

    /**
     * Creates the Game database pojo
     *
     * @param name game title
     * @param description game description
     * @param imagePath game image path on disk
     * @param platform game platform
     * @return game database object
     */
    public static Game createGame(String name, String description, String imagePath, Platform platform) {
        Game game = new Game();
        game.setName(name);
        game.setDescription(description);
        game.setImagePath(imagePath);
        game.setPlatform(platform);
        game.setReel(ExtractorUtils.getValue(InfoTypes.REEL.getName(), description));
        game.setWay(ExtractorUtils.getValue(InfoTypes.WAY.getName(), description));
        game.setLine(ExtractorUtils.getValue(InfoTypes.LINE.getName(), description));
        game.setCredit(ExtractorUtils.getValue(InfoTypes.CREDIT.getName(), description));
        return game;

    }
}
