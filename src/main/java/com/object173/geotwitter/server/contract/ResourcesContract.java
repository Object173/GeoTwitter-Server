package com.object173.geotwitter.server.contract;

import java.io.File;

public class ResourcesContract {
    private static final String RESOURCES_PATH = File.separator + "resources";

    private static final String IMAGE_PATH = File.separator + "images";
    public static final String IMAGE_FORMAT = "png";

    private static final String AVATAR_PATH = IMAGE_PATH + "/avatar";
    private static final String AVATAR_PREFIX = "_avatar";

    public static String createAvatarPath(final String login) {
        if(login == null) {
            return null;
        }
        return System.getProperty("catalina.home") + RESOURCES_PATH + AVATAR_PATH + File.separator +
                login + AVATAR_PREFIX + "." + IMAGE_FORMAT;
    }
}
