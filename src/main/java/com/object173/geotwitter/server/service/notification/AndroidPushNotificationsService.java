package com.object173.geotwitter.server.service.notification;

import com.google.gson.Gson;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.AuthProfile;
import com.object173.geotwitter.server.json.DialogJson;
import com.object173.geotwitter.server.json.MessageJson;
import com.object173.geotwitter.server.service.ContactsService;
import com.object173.geotwitter.server.service.user.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AndroidPushNotificationsService {

    private static final Gson gson = new Gson();
    private static final String KEY_TYPE = "type";
    private static final String KEY_OBJECT = "object";
    private static final String TYPE_INVITE = "invite";
    private static final String TYPE_CHANGE_USER = "change_user";
    private static final String TYPE_CHANGE_AVATAR = "change_avatar";
    private static final String TYPE_NEW_DIALOG = "new_dialog";
    private static final String TYPE_NEW_MESSAGE = "new_message";
    private static final String FIREBASE_SERVER_KEY =
            "AAAALw5heok:APA91bHYv1h7AqpnzC0xmf_0rTbsNvzWSpkquoeLJHFjh9go-lZI-5TeCUchEcKNEVVGGzqeZa3E9gYmZBwnMh4QNv6ruXaEbjJcTD6pRMPk9GtP3WYdXIwcvecdU0Keq9UfUMmkyYax";
    private static final String FIREBASE_SERVER_URL = "https://fcm.googleapis.com/fcm/send";
    @Autowired
    private ContactsService contactsService;
    @Autowired
    private UserService userService;

    @Async
    public void sendNewMessage(final User user, final MessageJson message) {
        if(user == null || message == null) {
            return;
        }
        send(user.getFcmToken(), createData(TYPE_NEW_MESSAGE, message));
    }

    @Async
    public void sendNewDialog(final User user, final DialogJson dialog) {
        if(user == null || dialog == null) {
            return;
        }
        send(user.getFcmToken(), createData(TYPE_NEW_DIALOG, dialog));
    }

    @Async
    public void sendChangeUser(final long userId) {
        sendDataToContacts(userId, TYPE_CHANGE_USER);
    }

    @Async
    public void sendChangeAvatar(final long userId) {
        sendDataToContacts(userId, TYPE_CHANGE_AVATAR);
    }

    private void sendDataToContacts(final long userId, final String type) {
        final User user = userService.get(userId);
        if(user == null || type == null) {
            return;
        }
        final List<User> contactsList =  contactsService.getContactList(user);
        if(contactsList == null) {
            return;
        }

        for (final User contact: contactsList) {
            final AuthProfile profile = new AuthProfile(user);
            send(contact.getFcmToken(), createData(type, profile));
        }
    }

    @Async
    public void sendInvite(final String fcmToken, final AuthProfile contact) {
        if(fcmToken == null || contact == null) {
            return;
        }

        send(fcmToken, createData(TYPE_INVITE, contact));
    }

    private JSONObject createData(final String type, final Object data) {
        final JSONObject result = new JSONObject();
        result.put(KEY_TYPE, type);
        result.put(KEY_OBJECT, gson.toJson(data));
        return result;
    }

    private void send(final String fcmToken, final JSONObject data) {
        if(fcmToken == null || data == null) {
            return;
        }

        final JSONObject body = new JSONObject();

        body.put("to", fcmToken);
        body.put("priority", "high");
        body.put("data", data);

        final HttpEntity<String> request = new HttpEntity<String>(body.toString());

        final RestTemplate restTemplate = new RestTemplate();

        final ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        restTemplate.postForObject(FIREBASE_SERVER_URL, request, FirebaseResponse.class);
    }
}
