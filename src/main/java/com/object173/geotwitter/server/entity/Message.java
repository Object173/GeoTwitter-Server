package com.object173.geotwitter.server.entity;

import com.object173.geotwitter.server.json.MessageJson;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dialog_id", nullable = false)
    private Dialog dialog;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "text", length = 500)
    private String text;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "marker_id")
    private Marker marker;

    @Column(name = "date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Message() {
    }

    public static Message newInstance(final Dialog dialog, final User sender, final MessageJson messageJson) {
        if(dialog == null || sender == null || messageJson == null) {
            return null;
        }
        final Message message = new Message();
        message.dialog = dialog;
        message.sender = sender;
        message.text = messageJson.getText();
        message.date = new Date();
        message.marker = Marker.newInstance(messageJson.getMarker());
        return message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
