package com.object173.geotwitter.server.service.user;

import com.object173.geotwitter.server.contract.ServiceContract;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.AuthToken;
import com.object173.geotwitter.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private ProfileService profileService;

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

    public final User insert(final User user, final byte[] avatarBytes) {
        if(user == null) {
            return null;
        }

        final User newUser = repository.saveAndFlush(user);

        if(avatarBytes != null && avatarBytes.length > 0) {
            profileService.setUserAvatar(user.getProfile().getId(), user.getLogin(), avatarBytes);
        }
        return repository.findOne(newUser.getId());
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

    public final User findByToken(final AuthToken token) {
        if(token != null) {
            return repository.findByToken(token.getUserId(), token.getHashKey());
        }
        return null;
    }

    public boolean setUserFcmToken(final long id, final String token) {
        if(id <= ServiceContract.NULL_ID || token == null) {
            return false;
        }
        repository.deleteFcmToken(token);
        repository.setUserFcmToken(id, token);
        return true;
    }

    public final boolean setUserPassword(final long id, final String oldPassword, final String newPassword) {
        if(id > ServiceContract.NULL_ID && oldPassword != null && newPassword != null) {
            return repository.setUserPassword(id, oldPassword, newPassword) > ServiceContract.NULL_ID;
        }
        return false;
    }
}
