package org.kryptonmlt.damselbuster.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.kryptonmlt.damselbuster.pojos.Game;
import org.kryptonmlt.damselbuster.repositories.GameRepository;
import org.kryptonmlt.damselbuster.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes Games stored in database
 * @author Kurt
 */
@RestController()
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    /**
     * Retrieves all games
     * @return games info
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Game> getGames() {
        return (List<Game>) gameRepository.findAll();
    }

    /**
     * Retrieves 1 game
     * @param gameId game id
     * @return game pojo from db
     */
    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Game getGame(@PathVariable long gameId) {
        return gameRepository.findOne(gameId);
    }

    /**
     * Retrieves image of gameId
     * @param gameId game id
     * @param response the image
     * @throws Exception  when image cannot be retrieved
     */
    @RequestMapping(value = "/{gameId}/getImage", method = RequestMethod.GET)
    public void getImage(@PathVariable long gameId, HttpServletResponse response) throws Exception {

        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        try {
            Game game = gameRepository.findOne(gameId);
            BufferedImage image = (BufferedImage) ImageUtils.readImage(game.getImagePath());
            ImageIO.write(image, "jpeg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        byte[] imgByte = jpegOutputStream.toByteArray();

        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(imgByte);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

}
