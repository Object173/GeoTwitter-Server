package com.object173.geotwitter.server.controller;

import com.object173.geotwitter.server.contract.UrlContract;
import com.object173.geotwitter.server.entity.User;
import com.object173.geotwitter.server.json.AuthToken;
import com.object173.geotwitter.server.json.Filter;
import com.object173.geotwitter.server.json.NewPlaceJson;
import com.object173.geotwitter.server.json.PlaceFilter;
import com.object173.geotwitter.server.service.PlaceService;
import com.object173.geotwitter.server.utils.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(UrlContract.PLACE_CONTROLLER_PATH)
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = UrlContract.PLACE_ADD_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final NewPlaceJson addNewPlace(@RequestPart(name = "authToken") AuthToken authToken,
                                            @RequestPart(name = "place") NewPlaceJson place,
                                            @RequestPart(required = false) List<MultipartFile> images) {
        System.out.println("addNewPlace");
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        System.out.println("security success");
        if(images != null) {
            final List<byte[]> imageList = new ArrayList<byte[]>();
            for(MultipartFile file : images) {
                if(file != null && !file.isEmpty()) {
                    try {
                        imageList.add(file.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(imageList.size() > 0) {
                return placeService.addNewPlace(user, place, imageList);
            }
        }
        return placeService.addNewPlace(user, place, null);
    }

    @RequestMapping(value = UrlContract.PLACE_GET_LAST_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final List<NewPlaceJson> getLastPlaces(@RequestPart(name = "authToken") AuthToken authToken,
                                          @RequestPart(name = "authorId") long authorId,
                                          @RequestPart(name = "lastId") long lastId) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        return placeService.getLastPlaces(user, authorId, lastId);
    }

    @RequestMapping(value = UrlContract.PLACE_GET_LAST_ALL_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final List<NewPlaceJson> getLastPlaces(@RequestPart(name = "authToken") AuthToken authToken,
                                                  @RequestPart(name = "lastId") long lastId) {
        System.out.println("getLastPlaces");
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        return placeService.getLastPlaces(user, lastId);
    }

    @RequestMapping(value = UrlContract.PLACE_GET_LIST_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final List<NewPlaceJson> getLastPlaces(@RequestPart(name = "authToken") AuthToken authToken,
                                                  @RequestPart(name = "authorId") long authorId,
                                                  @RequestPart(name = "filter") Filter filter) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        return placeService.getPlaceList(user, authorId, filter);
    }

    @RequestMapping(value = UrlContract.PLACE_GET_LIST_ALL_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final List<NewPlaceJson> getLastPlaces(@RequestPart(name = "authToken") AuthToken authToken,
                                                  @RequestPart(name = "filter") Filter filter) {
        final User user = securityService.secureRequest(authToken);
        if(user == null) {
            return null;
        }
        return placeService.getPlaceList(user, filter);
    }

    @RequestMapping(value = UrlContract.PLACE_GET_ALL_PATH, method = RequestMethod.POST)
    @ResponseBody
    public final List<NewPlaceJson> getAllPlaces(@RequestPart(name = "authToken") AuthToken authToken,
                                                 @RequestPart(name = "filter")PlaceFilter filter) {
        final User user = securityService.secureRequest(authToken);
        if(user == null || filter == null) {
            return null;
        }
        return placeService.getPlaceList(filter);
    }
}
