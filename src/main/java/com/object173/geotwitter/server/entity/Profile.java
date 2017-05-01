package com.object173.geotwitter.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "profiles")
public final class Profile {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "username", nullable = false, length = 15)
    private String username;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "avatar_url")
    private String avatar_url;

    public Profile() {
    }

    public Profile(final String username, final String status, final String avatar_url) {
        this.username = username;
        this.status = status;
        this.avatar_url = avatar_url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
