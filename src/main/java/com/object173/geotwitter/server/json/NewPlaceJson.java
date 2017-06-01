package com.object173.geotwitter.server.json;

import com.object173.geotwitter.server.contract.UrlContract;
import com.object173.geotwitter.server.entity.Image;
import com.object173.geotwitter.server.entity.NewPlace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class NewPlaceJson {

    private long id;
    private long authorId;
    private String title;
    private String body;
    private MarkerJson marker;

    private Date date;

    private List<String> images;

    public NewPlaceJson() {
    }

    public static NewPlaceJson newInstance(final NewPlace newPlace) {
        if(newPlace == null) {
            return null;
        }
        final NewPlaceJson newPlaceJson = new NewPlaceJson();
        newPlaceJson.setId(newPlace.getId());
        if(newPlace.getAuthor() != null) {
            newPlaceJson.setAuthorId(newPlace.getAuthor().getId());
        }
        newPlaceJson.setTitle(newPlace.getTitle());
        newPlaceJson.setBody(newPlace.getBody());
        newPlaceJson.setMarker(MarkerJson.newInstance(newPlace.getMarker()));
        newPlaceJson.setDate(newPlace.getDate());

        final Set<Image> imageSet = newPlace.getImages();
        if(imageSet != null) {
            final List<String> imageList = new ArrayList<String>();
            for(Image image : imageSet) {
                imageList.add(UrlContract.getImageUrl(image.getId()));
            }
            newPlaceJson.setImages(imageList);
        }
        return newPlaceJson;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MarkerJson getMarker() {
        return marker;
    }

    public void setMarker(MarkerJson marker) {
        this.marker = marker;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
