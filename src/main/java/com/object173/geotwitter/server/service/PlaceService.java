package com.object173.geotwitter.server.service;

import com.object173.geotwitter.server.contract.ServiceContract;
import com.object173.geotwitter.server.entity.Image;
import com.object173.geotwitter.server.entity.Marker;
import com.object173.geotwitter.server.entity.NewPlace;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.Filter;
import com.object173.geotwitter.server.json.NewPlaceJson;
import com.object173.geotwitter.server.json.PlaceFilter;
import com.object173.geotwitter.server.repository.PlaceRepository;
import com.object173.geotwitter.server.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private UserService userService;

    private static double deg2rad(final double degree) {
        return degree * (Math.PI / 180);
    }

    public final NewPlaceJson addNewPlace(final User user, final NewPlaceJson newPlaceJson, final List<byte[]> imageList) {
        if(user == null || newPlaceJson == null) {
            return null;
        }
        final NewPlace newPlace = NewPlace.newInstance(newPlaceJson);
        newPlace.setAuthor(user);

        if(imageList != null && imageList.size() > 0) {
            final Set<Image> images = new HashSet<Image>();
            for (byte[] bytes : imageList) {
                if(bytes == null || bytes.length <= 0) {
                    continue;
                }
                final Image image = imageService.createAndSaveImage(bytes);
                images.add(image);
            }
            newPlace.setImages(images);
        }

        final NewPlace newNewPlace = placeRepository.saveAndFlush(newPlace);
        if(newNewPlace == null) {
            return null;
        }
        return NewPlaceJson.newInstance(newNewPlace);
    }

    public final List<NewPlaceJson> getLastPlaces(final User user, final long lastId) {
        System.out.println("getLastPlaces");
        if(user == null) {
            System.out.println("user == null");
            return null;
        }
        final List<User> contactList = contactsService.getContactList(user);
        if(contactList == null) {
            System.out.println("contactList == null");
            return null;
        }
        final List<NewPlace> placeList = placeRepository.getLastPlaces(lastId, contactList);
        if(placeList == null) {
            System.out.println("placeList == null");
        }
        return toJson(placeList);
    }

    public final List<NewPlaceJson> getLastPlaces(final User user, final long authorId, final long lastId) {
        if(user == null) {
            return null;
        }
        final User author = userService.get(authorId);
        if(author == null) {
            return null;
        }
        final List<NewPlace> placeList = placeRepository.getLastPlaces(lastId, author);
        return toJson(placeList);
    }

    public final List<NewPlaceJson> getPlaceList(final User user, final Filter filter) {
        if(user == null || filter == null) {
            return null;
        }
        final List<User> contactList = contactsService.getContactList(user);
        if(contactList == null || contactList.size() <= 0) {
            return new ArrayList<NewPlaceJson>();
        }

        final int page = filter.getOffset() / filter.getSize() +
                ((filter.getOffset() % filter.getSize() > 0) ? 1 : 0);
        final List<NewPlace> placeList = placeRepository.getPlaceList(contactList, new PageRequest(page, filter.getSize()));
        return toJson(placeList);
    }

    public final List<NewPlaceJson> getPlaceList(final User user, final long authorId, final Filter filter) {
        if(user == null || filter == null) {
            return null;
        }
        final User author = userService.get(authorId);
        if(author == null) {
            return null;
        }

        final int page = filter.getOffset() / filter.getSize() +
                ((filter.getOffset() % filter.getSize() > 0) ? 1 : 0);
        final List<NewPlace> placeList = placeRepository.getPlaceList(author, new PageRequest(page, filter.getSize()));
        return toJson(placeList);
    }

    public final List<NewPlaceJson> getPlaceList(final PlaceFilter filter) {
        if(filter == null || filter.getMarkerJson() == null || filter.getDistance() < ServiceContract.DISTANCE_MIN ||
                filter.getDays() < ServiceContract.DAYS_MIN) {
            return null;
        }

        final Date date = new Date(System.currentTimeMillis() - filter.getDays() * 24 * 60 * 60 * 1000);
        final Marker marker = Marker.newInstance(filter.getMarkerJson());

        final List<NewPlace> placeList = placeRepository
                .getPlaceList(filter.getSearch(), date);

        final List<NewPlaceJson> result = new ArrayList<NewPlaceJson>();
        for(NewPlace place : placeList) {
            if(calcDistance(place.getMarker(), marker) <= filter.getDistance()) {
                result.add(NewPlaceJson.newInstance(place));
            }
        }
        return result;
    }

    private double calcDistance(final Marker marker1, final Marker marker2) {
        if(marker1 == null || marker2 == null) {
            return Double.MAX_VALUE;
        }

        final double dlng = deg2rad(marker1.getLongitude() - marker2.getLongitude());
        final double dlat = deg2rad(marker1.getLatitude() - marker2.getLatitude());
        final double a = sin(dlat / 2) * sin(dlat / 2) + cos(deg2rad(marker2.getLatitude()))
                * cos(deg2rad(marker1.getLatitude())) * sin(dlng / 2) * sin(dlng / 2);
        return 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * 6371000;
    }

    private List<NewPlaceJson> toJson(final List<NewPlace> placeList) {
        System.out.println("toJson");
        if(placeList == null) {
            return null;
        }
        final List<NewPlaceJson> result = new ArrayList<NewPlaceJson>();
        for(NewPlace place : placeList) {
            result.add(NewPlaceJson.newInstance(place));
        }
        return result;
    }
}
