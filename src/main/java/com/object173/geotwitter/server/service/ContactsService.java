package com.object173.geotwitter.server.service;

import com.object173.geotwitter.server.entity.Relation;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.AuthProfile;
import com.object173.geotwitter.server.json.Filter;
import com.object173.geotwitter.server.repository.RelationRepository;
import com.object173.geotwitter.server.repository.UserRepository;
import com.object173.geotwitter.server.service.notification.AndroidPushNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private AndroidPushNotificationsService notificationsService;

    public final AuthProfile getContact(final long userId, final long contactId) {
        final User user = userRepository.findOne(contactId);
        final Relation relation = relationRepository.findRelation(userId, contactId);

        if(user != null && user.getProfile() != null && relation != null) {
            return new AuthProfile(user, relation);
        }
        return null;
    }

    public final List<AuthProfile> getContactList(final long userId) {
        final List<Relation> relationList = relationRepository.findAllRelation(userId);

        if(relationList == null) {
            return null;
        }
        final List<AuthProfile> profileList = new ArrayList<AuthProfile>();
        for(Relation relation: relationList) {
            final User user;
            if(relation.getUser1().getId() == userId) {
                user = relation.getUser2();
            }
            else {
                user = relation.getUser1();
            }
            if(user != null) {
                profileList.add(new AuthProfile(user, relation));
            }
        }
        return profileList;
    }

    public final List<User> getContactList(final User user) {
        final List<Relation> relationList = relationRepository.findAllRelation(user.getId());

        if(relationList == null) {
            return null;
        }
        final List<User> userList = new ArrayList<User>();
        for(Relation relation: relationList) {
            final User contact;
            if(relation.getUser1().getId() == user.getId()) {
                contact = relation.getUser2();
            }
            else {
                contact = relation.getUser1();
            }
            if(contact != null) {
                userList.add(contact);
            }
        }
        return userList;
    }

    public final List<AuthProfile> getContactList(final long userId, final Filter filter) {
        final User user = userRepository.findOne(userId);
        if(user == null || filter == null) {
            return null;
        }
        final int page = filter.getOffset() / filter.getSize() +
                ((filter.getOffset() % filter.getSize() > 0) ? 1 : 0);
        final List<User> userList = userRepository.findByFilter(userId, filter.getRequest(),
                new PageRequest(page, filter.getSize()));
        if(userList == null) {
            return null;
        }
        final List<AuthProfile> contactList = new ArrayList<AuthProfile>();
        for(User contact: userList) {
            final Relation relation = relationRepository.getRelation(user, contact);
            contactList.add(new AuthProfile(contact, relation));
        }
        return contactList;
    }

    public AuthProfile addInvite(final long userId, final long contactId) {
        final User user = userRepository.findOne(userId);
        final User contact = userRepository.findOne(contactId);

        if(user == null || contact == null) {
            return null;
        }

        final Relation relation = relationRepository.getRelation(user, contact);
        if(relation == null) {
            relationRepository.saveAndFlush(
                    new Relation(user, true, contact, false));
        }
        else {
            if((relation.getUser1().getId() == user.getId() && relation.isContact1()) ||
                    (relation.getUser2().getId() == user.getId() && relation.isContact2())) {
                return null;
            }
            relationRepository.setContact1(user, contact, true);
            relationRepository.setContact2(user, contact, true);
        }
        notificationsService.sendInvite(contact.getFcmToken(), getContact(contactId, userId));
        return getContact(userId, contactId);
    }

    public AuthProfile removeInvite(final long userId, final long contactId) {
        final User user = userRepository.findOne(userId);
        final User contact = userRepository.findOne(contactId);

        if(user == null || contact == null) {
            return null;
        }

        final Relation relation = relationRepository.getRelation(user, contact);
        if(relation != null) {
            if((relation.getUser1().getId() == user.getId() && !relation.isContact1()) ||
                    (relation.getUser2().getId() == user.getId() && !relation.isContact2())) {
                return null;
            }
            relationRepository.setContact1(user, contact, false);
            relationRepository.setContact2(user, contact, false);

            notificationsService.sendInvite(contact.getFcmToken(), getContact(contactId, userId));
            return getContact(userId, contactId);
        }
        return null;
    }
}
