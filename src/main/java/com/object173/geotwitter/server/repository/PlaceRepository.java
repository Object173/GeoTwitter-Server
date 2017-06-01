package com.object173.geotwitter.server.repository;

import com.object173.geotwitter.server.entity.NewPlace;
import com.object173.geotwitter.server.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PlaceRepository extends JpaRepository<NewPlace, Long>{

    @Query("SELECT place FROM NewPlace place where place.author = :user and place.id > :lastId ORDER BY place.date DESC")
    List<NewPlace> getLastPlaces(@Param("lastId") long lastId, @Param("user") User user);

    @Query("SELECT place FROM NewPlace place where place.author in :userList and place.id > :lastId ORDER BY place.date DESC")
    List<NewPlace> getLastPlaces(@Param("lastId") long lastId, @Param("userList") List<User> userList);

    @Query("SELECT place FROM NewPlace place where place.author = :user ORDER BY place.date DESC")
    List<NewPlace> getPlaceList(@Param("user") User user, Pageable pageable);

    @Query("SELECT place FROM NewPlace place where place.author in :userList ORDER BY place.date DESC")
    List<NewPlace> getPlaceList(@Param("userList") List<User> user, Pageable pageable);

    @Query("SELECT place FROM NewPlace place where place.date > :date and place.marker <> null and " +
            "place.title LIKE CONCAT('%', :search, '%') ORDER BY place.date DESC")
    List<NewPlace> getPlaceList(@Param("search") String search, @Param("date") Date date);
}
