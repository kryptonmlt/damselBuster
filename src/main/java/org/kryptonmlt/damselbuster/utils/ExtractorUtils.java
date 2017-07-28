package org.kryptonmlt.damselbuster.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kurt
 */
public class ExtractorUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractorUtils.class);

    private ExtractorUtils() {

    }

    /**
     * Searches for the type in text and returns its value Example 100-Reel
     * converted to 100
     *
     * @param text
     * @param type
     * @return type value if found, otherwise -1
     */
    public static int getValue(String type, String text) {
        int result = -1;
        String[] pairs = text.split(" ");
        for (String pair : pairs) {
            if (pair.contains("-")) {
                String[] value = pair.split("-");
                if (value.length > 1) {
                    if (type.equals(value[1])) {
                        try {
                            String temp = value[0].replaceAll("[^\\d.]", "");
                            if (!temp.isEmpty()) {
                                result = Integer.parseInt(temp);
                            }
                        } catch (Exception e) {
                            LOGGER.error("Invalid number", e);
                        }
                        return result;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Removes trailing characters from text
     *
     * @param text
     * @param w
     * @return Example Advantage (100) can be converted to Advantage
     */
    public static String stripWord(String text, String trailing) {
        return text.substring(0, text.indexOf(trailing)).trim();
    }
}
