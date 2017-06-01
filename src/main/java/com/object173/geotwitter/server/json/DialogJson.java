package com.object173.geotwitter.server.json;

import com.object173.geotwitter.server.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class DialogJson {

    private long id;
    private AuthProfile companion;
    private List<MessageJson> messageList;

    public DialogJson() {
    }

    public static DialogJson newInstance(final long dialogId, final AuthProfile companion, final List<Message> messageList) {
        if(companion == null) {
            return null;
        }
        final DialogJson newDialog = new DialogJson();
        newDialog.setId(dialogId);
        newDialog.setCompanion(companion);

        if(messageList != null && messageList.size() > 0) {
            newDialog.messageList = new ArrayList<MessageJson>();
            for (Message message : messageList) {
                newDialog.messageList.add(MessageJson.newInstance(message));
            }
        }
        return newDialog;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public AuthProfile getCompanion() {
        return companion;
    }

    public void setCompanion(AuthProfile companion) {
        this.companion = companion;
    }

    public List<MessageJson> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageJson> messageList) {
        this.messageList = messageList;
    }
}
