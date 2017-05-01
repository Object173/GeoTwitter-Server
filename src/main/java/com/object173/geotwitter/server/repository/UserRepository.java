package com.object173.geotwitter.server.repository;

import com.object173.geotwitter.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by ярослав on 29.04.2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user FROM User user where user.id = :id and user.hash_key = :hash_key")
    User findByToken(@Param("id") Long id, @Param("hash_key") String hash_key);

    @Query("SELECT user FROM User user where user.login = :login")
    User findByLogin(@Param("login") String login);
}
