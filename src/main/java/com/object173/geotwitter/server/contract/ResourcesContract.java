package com.object173.geotwitter.server.contract;

public class ResourcesContract {
    public static final String IMAGE_FORMAT = "png";
    private static final String SEPARATOR = "\\";
    private static final String RESOURCES_PATH = "resources" + SEPARATOR;
    private static final String IMAGE_PATH = RESOURCES_PATH + "images" + SEPARATOR;
    private static final String AVATAR_PATH = IMAGE_PATH + "avatar" + SEPARATOR;
    private static final String AVATAR_PREFIX = "_avatar";
    private static final String MINI_PREFIX = "_mini";

    public static String getAbsolutePath(final String path) {
        return System.getProperty("catalina.home") + SEPARATOR + path;
    }

    public static String getAvatarPath() {
        return AVATAR_PATH;
    }

    public static String getImagePath() {
        return IMAGE_PATH;
    }

    public static String createImageName (final long id) {
        return String.valueOf(id) + "." + IMAGE_FORMAT;
    }

    public static String createAvatarName(final String login) {
        return  login + AVATAR_PREFIX + "." + IMAGE_FORMAT;
    }

    public static String createMiniAvatarName(final String login) {
        return  login + AVATAR_PREFIX + MINI_PREFIX + "." + IMAGE_FORMAT;
    }
}
