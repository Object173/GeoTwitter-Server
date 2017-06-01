package com.object173.geotwitter.server.repository;

import com.object173.geotwitter.server.entity.Image;
import com.object173.geotwitter.server.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Profile profile set profile.username = :username where profile.id = :id")
    int setUserName(@Param("id") Long id, @Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE Profile profile set profile.status = :status where profile.id = :id")
    int setUserStatus(@Param("id") Long id, @Param("status") String status);

    @Modifying
    @Transactional
    @Query("UPDATE Profile profile set profile.avatar = :avatar where profile.id = :id")
    int setUserAvatar(@Param("id") Long id, @Param("avatar") Image avatar);

    @Modifying
    @Transactional
    @Query("UPDATE Profile profile set profile.avatarMini = :avatarMini where profile.id = :id")
    int setUserMiniAvatar(@Param("id") Long id, @Param("avatarMini") Image avatarMini);
}
