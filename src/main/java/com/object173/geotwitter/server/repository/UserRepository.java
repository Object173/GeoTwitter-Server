package com.object173.geotwitter.server.repository;

import com.object173.geotwitter.server.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user where user.id <> :userId AND user.profile.username LIKE CONCAT('%', :request, '%')")
    List<User> findByFilter(@Param("userId") long userId, @Param("request") String request, Pageable pageable);

    @Query("SELECT user FROM User user where user.id = :id and user.hashKey = :hashKey")
    User findByToken(@Param("id") Long id, @Param("hashKey") String hashKey);

    @Query("SELECT user FROM User user where user.login = :login")
    User findByLogin(@Param("login") String login);

    @Modifying
    @Transactional
    @Query("UPDATE User user set user.fcmToken = null where user.fcmToken = :token")
    void deleteFcmToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("UPDATE User user set user.fcmToken = :token where user.id = :id")
    void setUserFcmToken(@Param("id") Long id, @Param("token") String token);

    @Modifying
    @Transactional
    @Query("UPDATE User user set user.password = :newPassword where user.id = :id and user.password = :oldPassword")
    int setUserPassword(@Param("id") Long id, @Param("oldPassword") String oldPassword,
                         @Param("newPassword") String newPassword);
}
