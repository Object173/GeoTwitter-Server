package com.object173.geotwitter.server.json;

import com.object173.geotwitter.server.contract.UrlContract;
import com.object173.geotwitter.server.entity.Profile;
import com.object173.geotwitter.server.entity.Relation;
import com.object173.geotwitter.server.entity.User;

public class AuthProfile {

    private long userId;
    private String username;
    private String status;
    private String avatarUrl;
    private String avatarUrlMini;
    private RelationStatus relation;

    public AuthProfile(String username, String status, String avatarUrl, String avatarUrlMini) {
        this.username = username;
        this.status = status;
        this.avatarUrl = avatarUrl;
        this.avatarUrlMini = avatarUrlMini;
    }

    public AuthProfile(String username, String status, String avatarUrl) {
        this(username, status, avatarUrl, null);
    }

    public AuthProfile(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public AuthProfile(final Profile profile, final String login) {
        if(profile != null) {
            this.username = profile.getUsername();
            this.status = profile.getStatus();
            if(profile.getAvatar() != null) {
                this.avatarUrl = UrlContract.getAvatarUrl(login);
            }
            if(profile.getAvatarMini() != null) {
                this.avatarUrlMini = UrlContract.getAvatarMiniUrl(login);
            }
        }
    }

    public AuthProfile(final User user) {
        this(user.getProfile(), user.getLogin());
        this.userId = user.getId();
    }

    public AuthProfile(final User user, final Relation relation) {
        this(user);

        if(relation == null) {
            this.relation = RelationStatus.NONE;
            return;
        }
        if(relation.getUser2().getId() == user.getId()) {
            if (!relation.isContact1() && !relation.isContact2()) {
                this.relation = RelationStatus.NONE;
            } else if (relation.isContact1() && relation.isContact2()) {
                this.relation = RelationStatus.CONTACT;
            } else if (relation.isContact1() && !relation.isContact2()) {
                this.relation = RelationStatus.INVITE;
            } else if (!relation.isContact1() && relation.isContact2()) {
                this.relation = RelationStatus.SUBSCRIBER;
            }
        }
        else {
            if (!relation.isContact2() && !relation.isContact1()) {
                this.relation = RelationStatus.NONE;
            } else if (relation.isContact2() && relation.isContact1()) {
                this.relation = RelationStatus.CONTACT;
            } else if (relation.isContact2() && !relation.isContact1()) {
                this.relation = RelationStatus.INVITE;
            } else if (!relation.isContact2() && relation.isContact1()) {
                this.relation = RelationStatus.SUBSCRIBER;
            }
        }
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrlMini() {
        return avatarUrlMini;
    }

    public void setAvatarUrlMini(String avatarUrlMini) {
        this.avatarUrlMini = avatarUrlMini;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public RelationStatus getRelation() {
        return relation;
    }

    public void setRelation(RelationStatus relation) {
        this.relation = relation;
    }

    enum RelationStatus{
        NONE,
        CONTACT,
        SUBSCRIBER,
        INVITE
    }
}
