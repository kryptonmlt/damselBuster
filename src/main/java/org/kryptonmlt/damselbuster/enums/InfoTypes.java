package org.kryptonmlt.damselbuster.enums;

/**
 *
 * @author Kurt
 */
public enum InfoTypes {
    REEL("Reel"),
    LINE("Line"),
    CREDIT("Credit"),
    WAY("Way");

    private String name;

    InfoTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
