package com.object173.geotwitter.server.service.user;

import com.object173.geotwitter.server.entity.User;

public interface UserService {
    User get(long id);
    User get(long id, String hash_key);
    User get(String login);
    User insert(User user);
    boolean update(User user);
    void remove(long id);
}
