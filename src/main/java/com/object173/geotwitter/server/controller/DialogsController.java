package com.object173.geotwitter.server.controller;

import com.object173.geotwitter.server.contract.UrlContract;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.AuthToken;
import com.object173.geotwitter.server.json.DialogJson;
import com.object173.geotwitter.server.json.Filter;
import com.object173.geotwitter.server.json.MessageJson;
import com.object173.geotwitter.server.service.DialogService;
import com.object173.geotwitter.server.service.user.UserService;
import com.object173.geotwitter.server.utils.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(UrlContract.DIALOG_CONTROLLER_PATH)
public class DialogsController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private DialogService dialogService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = UrlContract.DIALOG_GET_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final DialogJson getDialog(@RequestPart(name = "authToken") AuthToken authToken,
                                        @RequestPart(name = "companionId") long companionId) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        final User companion = userService.get(companionId);
        return dialogService.getDialog(user, companion);
    }

    @RequestMapping(value = UrlContract.DIALOG_GET_LAST_MESSAGES_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final List<MessageJson> getLastMessages(@RequestPart(name = "authToken") AuthToken authToken,
                                      @RequestPart(name = "dialogId") long dialogId,
                                      @RequestPart(name = "lastId") long lastId) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        return dialogService.getLastMessages(dialogId, lastId);
    }

    @RequestMapping(value = UrlContract.DIALOG_GET_ALL_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final List<DialogJson> getAllDialogs(@RequestPart(name = "authToken") AuthToken authToken) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        return dialogService.getAllDialogs(user);
    }

    @RequestMapping(value = UrlContract.DIALOG_GET_MESSAGES_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final List<MessageJson> getMessageList(@RequestPart(name = "authToken") AuthToken authToken,
                                                  @RequestPart(name = "dialogId") long dialogId,
                                                  @RequestPart(name = "filter", required = false) Filter filter) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        return dialogService.getMessageList(user, dialogId, filter);
    }

    @RequestMapping(value = UrlContract.DIALOG_SEND_MESSAGE_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final MessageJson sendMessage(@RequestPart(name = "authToken") AuthToken authToken,
                                         @RequestPart(name = "message") MessageJson message,
                                         @RequestPart(name = "image", required = false) MultipartFile image) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        final User companion = userService.get(message.getSenderId());

        if(image != null) {
            System.out.println("image != null");
            try {
                return dialogService.sendMessage(user, companion, message, image.getBytes());
            } catch (IOException e) {
                return null;
            }
        }
        return dialogService.sendMessage(user, companion, message, null);
    }
}
