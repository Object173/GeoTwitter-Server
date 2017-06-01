package com.object173.geotwitter.server.repository;

import com.object173.geotwitter.server.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerRepository extends JpaRepository<Marker, Long>  {
}
