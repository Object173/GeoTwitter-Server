package com.object173.geotwitter.server.controller;

import com.object173.geotwitter.server.contract.UrlContract;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.service.ImageService;
import com.object173.geotwitter.server.service.user.UserService;
import com.object173.geotwitter.server.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlContract.RESOURCES_CONTROLLER_PATH)
public class ResourcesController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = UrlContract.RESOURCES_AVATAR_PATH+UrlContract.SEPARATOR+"{login}",
            method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getAvatar(@PathVariable String login) {
        if(login == null) {
            return null;
        }
        final User user = userService.get(login);
        if(user != null && user.getProfile() != null && user.getProfile().getAvatar() != null) {
            return FileManager.readFile(user.getProfile().getAvatar().getUrl());
        }
        return null;
    }

    @RequestMapping(value = UrlContract.RESOURCES_AVATAR_MINI_PATH+UrlContract.SEPARATOR+"{login}",
            method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getAvatarMini(@PathVariable String login) {
        if(login == null) {
            return null;
        }
        final User user = userService.get(login);
        if(user != null && user.getProfile() != null && user.getProfile().getAvatarMini() != null) {
            return FileManager.readFile(user.getProfile().getAvatarMini().getUrl());
        }
        return null;
    }

    @RequestMapping(value = UrlContract.RESOURCES_IMAGE_PATH+UrlContract.SEPARATOR+"{id}",
            method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable String id) {
        System.out.println("getImage " + id);
        if(id == null) {
            return null;
        }
        final long imageId;
        try {
            imageId = Long.parseLong(id);
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
        return imageService.getImage(imageId);
    }
}
