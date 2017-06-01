package com.object173.geotwitter.server.json;

import com.object173.geotwitter.server.contract.UrlContract;
import com.object173.geotwitter.server.entity.User;

public class AuthResult {

    private Result result;
    private AuthProfile profile;
    private AuthToken token;
    public AuthResult() {
    }

    public AuthResult(final Result result, final AuthProfile profile, final AuthToken token) {
        this.result = result;
        this.profile = profile;
        this.token = token;
    }

    public AuthResult(final Result result) {
        this(result, null, null);
    }

    public AuthResult(final User user) {
        if(user != null) {
            this.result = Result.SUCCESS;
            if(user.getProfile() != null) {
                if(user.getProfile().getAvatar() != null && user.getProfile().getAvatar().getUrl() != null) {
                    this.profile = new AuthProfile(user.getProfile().getUsername(), user.getProfile().getStatus(),
                            UrlContract.getAvatarUrl(user.getLogin()));
                }
                else {
                    this.profile = new AuthProfile(user.getProfile().getUsername(), user.getProfile().getStatus());
                }
            }
            this.token = new AuthToken(user.getId(), user.getHashKey());
        }
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    public AuthProfile getProfile() {
        return profile;
    }

    public void setProfile(AuthProfile profile) {
        this.profile = profile;
    }

    public enum Result {
        SUCCESS,
        FAIL,
        USER_NOT_FOUND,
        WRONG_PASSWORD,
        INCORRECT_USERNAME,
        INCORRECT_LOGIN,
        INCORRECT_PASSWORD,
        LOGIN_EXISTS,
        NULL_POINTER,
        ACCESS_DENIED
    }
}
