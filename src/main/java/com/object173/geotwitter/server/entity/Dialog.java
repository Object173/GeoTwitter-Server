package com.object173.geotwitter.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "dialogs")
public class Dialog {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    public Dialog() {
    }

    public Dialog(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
