package com.object173.geotwitter.server.controller;

import com.object173.geotwitter.server.contract.UrlContract;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.AuthProfile;
import com.object173.geotwitter.server.json.AuthToken;
import com.object173.geotwitter.server.json.Filter;
import com.object173.geotwitter.server.service.ContactsService;
import com.object173.geotwitter.server.utils.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlContract.CONTACTS_CONTROLLER_PATH)
public class ContactsController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ContactsService contactsService;

    @RequestMapping(value = UrlContract.CONTACTS_GET_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final AuthProfile getContact(@RequestPart(name = "authToken") AuthToken authToken,
                                        @RequestPart(name = "contactId") long contactId) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        return contactsService.getContact(user.getId(), contactId);
    }

    @RequestMapping(value = UrlContract.CONTACTS_GET_ALL_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final List<AuthProfile> getAllContact(@RequestPart(name = "authToken") final AuthToken authToken,
                                                 @RequestPart(name = "filter", required = false) final Filter filter) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        if(filter == null) {
            return contactsService.getContactList(user.getId());
        }
        return contactsService.getContactList(user.getId(), filter);
    }

    @RequestMapping(value = UrlContract.CONTACTS_SEND_INVITE_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final AuthProfile sendInvite(@RequestPart(name = "authToken") final AuthToken authToken,
                                                 @RequestPart(name = "contactId") final long contactId) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        return contactsService.addInvite(user.getId(), contactId);
    }

    @RequestMapping(value = UrlContract.CONTACTS_REMOVE_INVITE_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final AuthProfile removeInvite(@RequestPart(name = "authToken") final AuthToken authToken,
                                        @RequestPart(name = "contactId") final long contactId) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        return contactsService.removeInvite(user.getId(), contactId);
    }
}
