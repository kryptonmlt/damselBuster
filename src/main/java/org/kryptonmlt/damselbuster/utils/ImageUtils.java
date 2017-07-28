package org.kryptonmlt.damselbuster.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kurt
 */
public class ImageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtils.class);

    private ImageUtils() {

    }

    /**
     * Downloads image from given url
     *
     * @param s url from where to download the image
     * @return the actual image
     */
    public static Image retrieveImage(String s) {
        Image image = null;
        try {
            URL url = new URL(s);
            image = ImageIO.read(url);
        } catch (Exception e) {
            LOGGER.error("Error in retrieving image ", e);
        }
        return image;
    }

    /**
     * Saves image to file
     *
     * @param img image to save
     * @param path path to save it
     * @param name file name
     * @return the file path where image is saved
     */
    public static String saveImage(Image img, String path, String name) {
        try {
            String fullPath = "";
            if (path == null || path.isEmpty()) {
                fullPath = name;
            } else {
                fullPath = path + "\\" + name;
            }

            File outputfile = new File(fullPath);
            if (img == null) {
                fullPath = "N/A";
            } else {
                ImageIO.write((BufferedImage) img, "png", outputfile);
            }
            return fullPath;
        } catch (IOException e) {
            LOGGER.error("Error in saving image ", e);
            return null;
        }
    }

    /**
     * Reads Image from file
     *
     * @param filename filename to read
     * @return Image read
     */
    public static Image readImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            LOGGER.error("Error in reading image ", e);
            return null;
        }
    }
}
