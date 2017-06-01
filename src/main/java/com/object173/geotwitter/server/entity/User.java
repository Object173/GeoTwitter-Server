package com.object173.geotwitter.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "login", nullable = false, length = 15, unique = true)
    private String login;

    @Column(name = "password", length = 25, nullable = false)
    private String password;

    @Column(name = "hash_key", nullable = false)
    private String hashKey;

    @Column(name = "fcm_token")
    private String fcmToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    public User() {
    }

    public User(String login, String password, String hashKey, Profile profile, String fcmToken) {
        this.login = login;
        this.password = password;
        this.hashKey = hashKey;
        this.fcmToken = fcmToken;
        this.profile = profile;
    }

    public User(final String login, final String password, final String hashKey, final Profile profile) {
        this(login, password, hashKey, profile, null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
