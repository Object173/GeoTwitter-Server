package com.object173.geotwitter.server.controller;

import com.object173.geotwitter.server.contract.AuthContract;
import com.object173.geotwitter.server.contract.UrlContract;
import com.object173.geotwitter.server.entity.Profile;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.AuthData;
import com.object173.geotwitter.server.json.AuthResult;
import com.object173.geotwitter.server.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(UrlContract.AUTH_CONTROLLER_PATH)
public final class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = UrlContract.AUTH_REGISTER_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final AuthResult register(@RequestPart(name = "authData") AuthData authData,
                                     @RequestPart(name = "avatar", required = false) MultipartFile avatar) {
        if (authData == null) {
            return new AuthResult(AuthResult.Result.NULL_POINTER);
        }
        final String username = authData.getUsername();
        final String login = authData.getLogin();
        final String password = authData.getPassword();
        final String fcmToken = authData.getFcmToken();

        if (username.length() < AuthContract.MIN_USERNAME_LENGTH) {
            return new AuthResult(AuthResult.Result.INCORRECT_USERNAME);
        }
        if (login.length() < AuthContract.MIN_LOGIN_LENGTH) {
            return new AuthResult(AuthResult.Result.INCORRECT_LOGIN);
        }
        if (password.length() < AuthContract.MIN_PASSWORD_LENGTH) {
            return new AuthResult(AuthResult.Result.INCORRECT_PASSWORD);
        }
        if (userService.get(login) != null) {
            return new AuthResult(AuthResult.Result.LOGIN_EXISTS);
        }

        final String hashKey = createHashKey(login);

        User newUser = new User(login, password, hashKey,
                new Profile(username, null, null), fcmToken);

        byte[] avatarBytes = null;
        if(avatar != null) {
            try {
                avatarBytes = avatar.getBytes();
            } catch (IOException e) {
                avatarBytes = null;
            }
        }
        newUser = userService.insert(newUser, avatarBytes);

        if (newUser == null) {
            return new AuthResult(AuthResult.Result.FAIL);
        }
        userService.setUserFcmToken(newUser.getId(), newUser.getFcmToken());
        return new AuthResult(newUser);
    }

    @RequestMapping(value = UrlContract.AUTH_SIGN_IN_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final AuthResult signIn(@RequestBody AuthData authData) {
        if (authData == null) {
            return new AuthResult(AuthResult.Result.NULL_POINTER);
        }

        final User user = userService.get(authData.getLogin());
        if (user == null) {
            return new AuthResult(AuthResult.Result.USER_NOT_FOUND);
        }

        if (user.getPassword().equals(authData.getPassword())) {
            userService.setUserFcmToken(user.getId(), authData.getFcmToken());
            return new AuthResult(user);
        }
        return new AuthResult(AuthResult.Result.WRONG_PASSWORD);
    }

    private String createHashKey(final String str) {
        if (str == null) {
            return null;
        }
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }
}


