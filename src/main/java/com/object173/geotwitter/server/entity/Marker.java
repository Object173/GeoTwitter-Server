package com.object173.geotwitter.server.entity;

import com.object173.geotwitter.server.json.MarkerJson;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "markers")
public class Marker {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    public Marker() {
    }

    public Marker(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Marker newInstance(final MarkerJson markerJson) {
        if(markerJson == null) {
            return null;
        }
        final Marker marker = new Marker();
        marker.latitude = markerJson.getLatitude();
        marker.longitude = markerJson.getLongitude();
        return marker;
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
