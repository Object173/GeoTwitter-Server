package com.object173.geotwitter.server.utils;

import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.AuthToken;
import com.object173.geotwitter.server.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private UserService userService;

    public User secureRequest(final AuthToken token) {
        if(token == null) {
            return null;
        }
        return userService.findByToken(token);
    }
}
