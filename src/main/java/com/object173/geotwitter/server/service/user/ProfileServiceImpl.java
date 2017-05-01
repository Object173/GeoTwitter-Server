package com.object173.geotwitter.server.service.user;

import com.object173.geotwitter.server.contract.ServiceContract;
import com.object173.geotwitter.server.entity.Profile;
import com.object173.geotwitter.server.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public final class ProfileServiceImpl implements ProfileService{
    @Autowired
    private ProfileRepository repository;

    public final Profile get(final long id) {
        return repository.findOne(id);
    }

    public final List<Profile> getAll() {
        return repository.findAll();
    }

    public final long insert(final Profile user) {
        if(user == null) {
            return ServiceContract.NULL_ID;
        }
        final Profile result = repository.saveAndFlush(user);

        if(result == null) {
            return ServiceContract.NULL_ID;
        }
        return result.getId();
    }

    public final boolean update(final Profile user) {
        if(user == null) {
            return false;
        }
        final Profile result = repository.saveAndFlush(user);

        return result != null;
    }

    public final void remove(final long id) {
        repository.delete(id);
    }
}
