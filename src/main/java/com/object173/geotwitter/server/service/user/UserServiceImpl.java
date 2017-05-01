package com.object173.geotwitter.server.service.user;

import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    public final User get(final long id) {
        return repository.findOne(id);
    }

    public final User get(final long id, final String hash_key) {
        if(hash_key == null) {
            return null;
        }
        return repository.findByToken(id, hash_key);
    }

    public User get(final String login) {
        return repository.findByLogin(login);
    }

    public final User insert(final User user) {
        if(user == null) {
            return null;
        }
        return repository.saveAndFlush(user);
    }

    public final boolean update(final User user) {
        if(user == null) {
            return false;
        }
        final User result = repository.saveAndFlush(user);

        return result != null;
    }

    public final void remove(final long id) {
        repository.delete(id);
    }
}
