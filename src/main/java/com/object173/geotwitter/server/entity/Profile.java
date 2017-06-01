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

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "status", length = 100)
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id")
    private Image avatar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_mini_id")
    private Image avatarMini;

    public Profile() {
    }

    public Profile(final String username, final String status, final Image avatar) {
        this.username = username;
        this.status = status;
        this.avatar = avatar;
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

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Image getAvatarMini() {
        return avatarMini;
    }

    public void setAvatarMini(Image avatarMini) {
        this.avatarMini = avatarMini;
    }
}
