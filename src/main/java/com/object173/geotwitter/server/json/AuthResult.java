package com.object173.geotwitter.server.json;

/**
 * Created by ������� on 29.04.2017.
 */
public class AuthResult {

    public enum Result {
        SUCCESS,
        FAIL,
        USER_NOT_FOUND,
        WRONG_PASSWORD,
        INCORRECT_USERNAME,
        INCORRECT_LOGIN,
        INCORRECT_PASSWORD,
        LOGIN_EXISTS,
        NULL_POINTER
    }

    private Result result;
    private AuthToken token;

    public AuthResult() {
    }

    public AuthResult(Result result, AuthToken token) {
        this.result = result;
        this.token = token;
    }

    public AuthResult(final Result result) {
        this(result, null);
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
}
