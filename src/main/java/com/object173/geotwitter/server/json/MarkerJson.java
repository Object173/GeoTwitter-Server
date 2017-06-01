package com.object173.geotwitter.server.json;

import com.object173.geotwitter.server.entity.Marker;

public class MarkerJson {

    private long id;
    private double latitude;
    private double longitude;

    public MarkerJson() {
    }

    public static MarkerJson newInstance(final Marker marker) {
        if(marker == null) {
            return null;
        }
        final MarkerJson markerJson = new MarkerJson();
        markerJson.id = marker.getId();
        markerJson.latitude = marker.getLatitude();
        markerJson.longitude = marker.getLongitude();
        return  markerJson;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
