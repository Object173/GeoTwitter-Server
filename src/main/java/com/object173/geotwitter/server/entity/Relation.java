package com.object173.geotwitter.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "relations")
public final class Relation {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user1_id")
    private User user1;

    @Column(name = "is_contact1")
    private boolean contact1;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user2_id")
    private User user2;

    @Column(name = "is_contact2")
    private boolean contact2;

    public Relation() {
    }

    public Relation(User user1, boolean contact1, User user2, boolean contact2) {
        this.user1 = user1;
        this.contact1 = contact1;
        this.user2 = user2;
        this.contact2 = contact2;
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

    public boolean isContact1() {
        return contact1;
    }

    public void setContact1(boolean contact1) {
        this.contact1 = contact1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public boolean isContact2() {
        return contact2;
    }

    public void setContact2(boolean contact2) {
        this.contact2 = contact2;
    }
}
