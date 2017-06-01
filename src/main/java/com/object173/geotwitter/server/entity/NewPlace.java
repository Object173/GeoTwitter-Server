package com.object173.geotwitter.server.entity;

import com.object173.geotwitter.server.json.NewPlaceJson;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "new_places")
public class NewPlace {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "body", nullable = false, length = 500)
    private String body;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "marker_id")
    private Marker marker;

    @Column(name = "date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Image> images;

    public NewPlace() {
    }

    public static NewPlace newInstance(final NewPlaceJson newPlaceJson) {
        if(newPlaceJson == null) {
            return null;
        }
        final NewPlace newPlace = new NewPlace();
        newPlace.setTitle(newPlaceJson.getTitle());
        newPlace.setBody(newPlaceJson.getBody());
        newPlace.setMarker(Marker.newInstance(newPlaceJson.getMarker()));
        newPlace.setDate(new Date());
        return newPlace;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
