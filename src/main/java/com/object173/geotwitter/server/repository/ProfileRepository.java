package com.object173.geotwitter.server.repository;

import com.object173.geotwitter.server.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ярослав on 29.04.2017.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
