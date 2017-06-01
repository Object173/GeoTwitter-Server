package com.object173.geotwitter.server.repository;

import com.object173.geotwitter.server.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Image image set image.url = :url where image.id = :id")
    int setUrl(@Param("id") Long id, @Param("url") String url);

}
