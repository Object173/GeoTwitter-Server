package com.object173.geotwitter.server.service.user;

import com.object173.geotwitter.server.entity.Profile;

import java.util.List;

public interface ProfileService {
    Profile get(long id);
    List<Profile> getAll();
    long insert(Profile user);
    boolean update(Profile user);
    void remove(long id);
}
