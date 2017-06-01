package com.object173.geotwitter.server.service.user;

import com.object173.geotwitter.server.contract.ResourcesContract;
import com.object173.geotwitter.server.contract.ServiceContract;
import com.object173.geotwitter.server.entity.Image;
import com.object173.geotwitter.server.entity.Profile;
import com.object173.geotwitter.server.repository.ProfileRepository;
import com.object173.geotwitter.server.service.ImageService;
import com.object173.geotwitter.server.service.notification.AndroidPushNotificationsService;
import com.object173.geotwitter.server.utils.FileManager;
import com.object173.geotwitter.server.utils.ImagesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ProfileService {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AndroidPushNotificationsService notificationsService;

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

    public final boolean setUserName(final long id, final String username) {
        if(id > ServiceContract.NULL_ID && username != null &&
                repository.setUserName(id, username) > ServiceContract.NULL_ID) {
            notificationsService.sendChangeUser(id);
            return true;
        }
        return false;
    }

    public final boolean setUserStatus(final long id, final String status) {
        if(id > ServiceContract.NULL_ID && status != null &&
                repository.setUserStatus(id, status) > ServiceContract.NULL_ID) {
            notificationsService.sendChangeUser(id);
            return true;
        }
        return false;
    }

    public final boolean setUserAvatar(final long id, final String login, final byte[] image) {
        if(id <= ServiceContract.NULL_ID || image == null) {
            return false;
        }
        if(image.length > 0) {
            final Profile profile = repository.findOne(id);
            imageService.remove(profile.getAvatar());
            final Image avatar = imageService.createImage(image,
                    ResourcesContract.getAvatarPath(), ResourcesContract.createAvatarName(login));
            repository.setUserAvatar(id, imageService.insert(avatar));

            //imageService.remove(profile.getAvatarMini());
            repository.setUserMiniAvatar(id, imageService.insert(createMiniAvatar(id, login)));

            notificationsService.sendChangeAvatar(id);
            return true;
        }
        return false;
    }

    private Image createMiniAvatar(final long id, final String login) {
        final Profile profile = repository.findOne(id);
        if(profile == null || login == null) {
            return null;
        }
        if(profile.getAvatar() == null) {
            return null;
        }
        final byte[] avatar = FileManager.readFile(profile.getAvatar().getUrl());
        final byte[] avatarMiniBytes = ImagesManager.createMiniAvatar(avatar);

        return imageService.createImage(avatarMiniBytes,
                ResourcesContract.getAvatarPath(), ResourcesContract.createMiniAvatarName(login));
    }

    public void removeAvatar(long id) {
        if(id <= ServiceContract.NULL_ID) {
            return;
        }
        final Profile profile = repository.findOne(id);
        if(profile.getAvatar() != null) {
            FileManager.deleteFile(profile.getAvatar().getUrl());
            imageService.remove(profile.getAvatar());
            repository.setUserAvatar(id, null);
        }
        if(profile.getAvatarMini() != null) {
            FileManager.deleteFile(profile.getAvatarMini().getUrl());
            imageService.remove(profile.getAvatarMini());
            repository.setUserMiniAvatar(id, null);
        }
        notificationsService.sendChangeAvatar(id);
    }

}
