package com.object173.geotwitter.server.json;

import com.object173.geotwitter.server.contract.UrlContract;
import com.object173.geotwitter.server.entity.Message;

import java.util.Date;

public class MessageJson {

    private long id;
    private long senderId;
    private long dialogId;
    private String text;
    private Date date;

    private String imageUrl;
    private MarkerJson marker;

    public MessageJson() {
    }

    public static MessageJson newInstance(final Message message) {
        if(message == null || message.getSender() == null ||
                message.getDialog() == null || message.getDate() == null) {
            return null;
        }
        final MessageJson newMessage = new MessageJson();
        newMessage.setId(message.getId());
        newMessage.setSenderId(message.getSender().getId());
        newMessage.setDialogId(message.getDialog().getId());
        newMessage.setText(message.getText());
        newMessage.setDate(message.getDate());

        if(message.getImage() != null) {
            newMessage.setImageUrl(UrlContract.getImageUrl(message.getImage().getId()));
        }

        newMessage.setMarker(MarkerJson.newInstance(message.getMarker()));
        return newMessage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getDialogId() {
        return dialogId;
    }

    public void setDialogId(long dialogId) {
        this.dialogId = dialogId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MarkerJson getMarker() {
        return marker;
    }

    public void setMarker(MarkerJson marker) {
        this.marker = marker;
    }
}
