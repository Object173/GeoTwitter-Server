package com.object173.geotwitter.server.controller;

import com.object173.geotwitter.server.contract.UrlContract;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.AuthProfile;
import com.object173.geotwitter.server.json.AuthResult;
import com.object173.geotwitter.server.json.AuthToken;
import com.object173.geotwitter.server.service.user.ProfileService;
import com.object173.geotwitter.server.service.user.UserService;
import com.object173.geotwitter.server.utils.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(UrlContract.PROFILE_CONTROLLER_PATH)
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = UrlContract.PROFILE_GET_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final AuthProfile getProfile(@RequestPart(name = "authToken") AuthToken authToken,
                                        @RequestPart(name = "userId") int userId) {
        final User user = securityService.secureRequest(authToken);
        if(user != null) {
            return new AuthProfile(user);
        }
        return null;
    }

    @RequestMapping(value = UrlContract.PROFILE_SET_FCM_CONTROLLER_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final void refreshFcmToken(@RequestPart(name = "authToken") AuthToken authToken,
                                            @RequestPart(name = "fcmToken") String fcmToken) {
        if(securityService.secureRequest(authToken)  != null) {
            userService.setUserFcmToken(authToken.getUserId(), fcmToken);
        }
    }

    @RequestMapping(value = UrlContract.PROFILE_SET_USERNAME_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final AuthResult.Result setUsername(@RequestPart(name = "authToken") AuthToken authToken,
                                        @RequestPart(name = "username") String username) {
        if(authToken == null || username == null) {
            return AuthResult.Result.NULL_POINTER;
        }
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return AuthResult.Result.ACCESS_DENIED;
        }
        if(profileService.setUserName(user.getProfile().getId(), username)) {
            return AuthResult.Result.SUCCESS;
        }
        return AuthResult.Result.FAIL;
    }

    @RequestMapping(value = UrlContract.PROFILE_SET_STATUS_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final AuthResult.Result setStatus(@RequestPart(name = "authToken") AuthToken authToken,
                                               @RequestPart(name = "status") String status) {
        if(authToken == null || status == null) {
            return AuthResult.Result.NULL_POINTER;
        }
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return AuthResult.Result.ACCESS_DENIED;
        }
        if(profileService.setUserStatus(user.getProfile().getId(), status)) {
            return AuthResult.Result.SUCCESS;
        }
        return AuthResult.Result.FAIL;
    }

    @RequestMapping(value = UrlContract.PROFILE_SET_PASSWORD_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final AuthResult.Result setPassword(@RequestPart(name = "authToken") AuthToken authToken,
                                             @RequestPart(name = "oldPassword") String oldPassword,
                                             @RequestPart(name = "newPassword") String newPassword) {
        if(authToken == null || oldPassword == null || newPassword == null) {
            return AuthResult.Result.NULL_POINTER;
        }
        if(securityService.secureRequest(authToken) == null) {
            return AuthResult.Result.ACCESS_DENIED;
        }
        if(userService.setUserPassword(authToken.getUserId(), oldPassword, newPassword)) {
            return AuthResult.Result.SUCCESS;
        }
        return AuthResult.Result.WRONG_PASSWORD;
    }

    @RequestMapping(value = UrlContract.PROFILE_SET_AVATAR_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final String setAvatar(@RequestPart(name = "authToken") AuthToken authToken,
                                     @RequestPart(name = "avatar") MultipartFile avatar) {
        if(authToken == null || avatar == null) {
            return null;
        }
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        try {
            if(profileService.setUserAvatar(user.getProfile().getId(), user.getLogin(), avatar.getBytes())) {
                return userService.get(authToken.getUserId()).getProfile().getAvatar().getUrl();
            }
        }
        catch (IOException e) {
            System.out.println("setAvatar fail");
        }
        return null;
    }

    @RequestMapping(value = UrlContract.PROFILE_REMOVE_AVATAR_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final AuthResult.Result removeAvatar(@RequestBody AuthToken authToken) {
        if(authToken == null) {
            return AuthResult.Result.NULL_POINTER;
        }
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return AuthResult.Result.ACCESS_DENIED;
        }
        profileService.removeAvatar(user.getProfile().getId());
        return AuthResult.Result.SUCCESS;
    }
}
