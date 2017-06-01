package com.object173.geotwitter.server.contract;

public class UrlContract {
    public static final String AUTH_CONTROLLER_PATH = "/auth";
    public static final String AUTH_REGISTER_PATH = "/register";
    public static final String AUTH_SIGN_IN_PATH = "/sign_in";

    public static final String RESOURCES_CONTROLLER_PATH = "/resources";
    public static final String RESOURCES_IMAGE_PATH = "/images";
    public static final String RESOURCES_AVATAR_PATH = "/avatar";
    public static final String RESOURCES_AVATAR_MINI_PATH = "/avatar_mini";

    public static final String PROFILE_CONTROLLER_PATH = "/profile";
    public static final String PROFILE_GET_PATH = "/get_profile";
    public static final String PROFILE_SET_FCM_CONTROLLER_PATH = "/set_fcm_token";
    public static final String PROFILE_SET_USERNAME_PATH = "/set_username";
    public static final String PROFILE_SET_STATUS_PATH = "/set_status";
    public static final String PROFILE_SET_PASSWORD_PATH = "/set_password";
    public static final String PROFILE_SET_AVATAR_PATH = "/set_avatar";
    public static final String PROFILE_REMOVE_AVATAR_PATH = "/remove_avatar";

    public static final String CONTACTS_CONTROLLER_PATH = "/contacts";
    public static final String CONTACTS_GET_PATH = "/get";
    public static final String CONTACTS_GET_ALL_PATH = "/get_all";
    public static final String CONTACTS_SEND_INVITE_PATH = "/send_invite";
    public static final String CONTACTS_REMOVE_INVITE_PATH = "/remove_invite";

    public static final String DIALOG_CONTROLLER_PATH = "/dialogs";
    public static final String DIALOG_GET_PATH = "/get";
    public static final String DIALOG_GET_ALL_PATH = "/get_all";
    public static final String DIALOG_GET_MESSAGES_PATH = "/get_messages";
    public static final String DIALOG_GET_LAST_MESSAGES_PATH = "/get_last_messages";
    public static final String DIALOG_SEND_MESSAGE_PATH = "/send_message";

    public static final String PLACE_CONTROLLER_PATH = "/place";
    public static final String PLACE_ADD_PATH = "/add";
    public static final String PLACE_GET_LIST_ALL_PATH = "/get_list_all";
    public static final String PLACE_GET_LIST_PATH = "/get_list";
    public static final String PLACE_GET_LAST_ALL_PATH = "/get_last_all";
    public static final String PLACE_GET_LAST_PATH = "/get_last";
    public static final String PLACE_GET_ALL_PATH = "/get_all";

    public static final String SEPARATOR = "/";

    public static String getAvatarUrl(final String login) {
        if(login == null) {
            return null;
        }
        return RESOURCES_CONTROLLER_PATH + RESOURCES_AVATAR_PATH + SEPARATOR + login;
    }

    public static String getAvatarMiniUrl(final String login) {
        if(login == null) {
            return null;
        }
        return RESOURCES_CONTROLLER_PATH + RESOURCES_AVATAR_MINI_PATH + SEPARATOR + login;
    }

    public static String getImageUrl(final long id) {
        return RESOURCES_CONTROLLER_PATH + RESOURCES_IMAGE_PATH + SEPARATOR + String.valueOf(id);
    }
}
