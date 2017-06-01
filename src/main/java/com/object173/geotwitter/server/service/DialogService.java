package com.object173.geotwitter.server.service;

import com.object173.geotwitter.server.contract.ServiceContract;
import com.object173.geotwitter.server.entity.Dialog;
import com.object173.geotwitter.server.entity.Message;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.AuthProfile;
import com.object173.geotwitter.server.json.DialogJson;
import com.object173.geotwitter.server.json.Filter;
import com.object173.geotwitter.server.json.MessageJson;
import com.object173.geotwitter.server.repository.DialogRepository;
import com.object173.geotwitter.server.repository.MessageRepository;
import com.object173.geotwitter.server.service.notification.AndroidPushNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DialogService {

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private AndroidPushNotificationsService notificationsService;

    @Autowired
    private ImageService imageService;

    public final List<MessageJson> getLastMessages(final long dialogId, final long lastMessageId) {
        final Dialog dialog = dialogRepository.findOne(dialogId);

        if(dialog == null) {
            return null;
        }

        final List<Message> messageList = messageRepository.getMessageList(dialog, lastMessageId);

        if(messageList != null && messageList.size() > 0) {
            final List<MessageJson> jsonList = new ArrayList<MessageJson>();
            for(Message message : messageList) {
                jsonList.add(MessageJson.newInstance(message));
            }
            return jsonList;
        }
        return null;
    }

    public final DialogJson getDialog(final User user, final User companion) {
        if(user == null || companion == null) {
            return null;
        }
        Dialog dialog = dialogRepository.getDialog(user, companion);
        final List<Message> messageList;
        if(dialog == null) {
            dialog = dialogRepository.saveAndFlush(new Dialog(user, companion));
            messageList = messageRepository.getMessageList(dialog, new PageRequest(0, ServiceContract.MESSAGE_BLOCK_SIZE));

            notificationsService.sendNewDialog(companion, DialogJson.newInstance(dialog.getId(),
                    contactsService.getContact(companion.getId(), user.getId()),messageList));
        }
        else {
            messageList = messageRepository.getMessageList(dialog, new PageRequest(0, ServiceContract.MESSAGE_BLOCK_SIZE));
        }
        final AuthProfile profile = contactsService.getContact(user.getId(), companion.getId());
        return DialogJson.newInstance(dialog.getId(), profile, messageList);
    }

    public final List<DialogJson> getAllDialogs(final User user) {
        if(user == null) {
            return null;
        }
        final List<Dialog> dialogList = dialogRepository.getDialogList(user);
        if(dialogList == null) {
            return null;
        }
        final List<DialogJson> result = new ArrayList<DialogJson>();
        for(Dialog dialog: dialogList) {

            final AuthProfile profile;
            if(dialog.getUser1().getId() == user.getId()) {
                profile = contactsService.getContact(user.getId(), dialog.getUser2().getId());
            }
            else {
                profile = contactsService.getContact(user.getId(), dialog.getUser1().getId());
            }

            result.add(DialogJson.newInstance(dialog.getId(), profile, null));
        }
        return result;
    }

    public final List<MessageJson> getMessageList(final User user, final long dialogId, final Filter filter) {
        final Dialog dialog = dialogRepository.findOne(dialogId);
        if(user == null || dialog == null) {
            return null;
        }
        final List<Message> messageList;
        if(filter != null) {
            final int page = filter.getOffset() / filter.getSize() +
                    ((filter.getOffset() % filter.getSize() > 0) ? 1 : 0);
            messageList = messageRepository.getMessageList(dialog, new PageRequest(page, filter.getSize()));
        }
        else {
            messageList = messageRepository.getMessageList(dialog);
        }
        if(messageList == null) {
            return null;
        }
        final List<MessageJson> result = new ArrayList<MessageJson>();
        for(Message message : messageList) {
            result.add(MessageJson.newInstance(message));
        }
        return result;
    }

    public final MessageJson sendMessage(final User user, final User companion, final MessageJson messageJson,
                                         final byte[] imageBytes) {
        if(user == null || companion == null || messageJson == null) {
            return null;
        }
        final Dialog dialog = dialogRepository.getDialog(user, companion);
        if(dialog == null) {
            return null;
        }
        final Message message = Message.newInstance(dialog, user, messageJson);
        if(imageBytes != null) {
            message.setImage(imageService.createAndSaveImage(imageBytes));
        }
        final Message newMessage = messageRepository.saveAndFlush(message);
        if(newMessage == null) {
            return null;
        }

        final MessageJson result = MessageJson.newInstance(newMessage);
        notificationsService.sendNewMessage(companion, result);
        return result;
    }
}
