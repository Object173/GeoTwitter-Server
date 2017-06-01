package com.object173.geotwitter.server.repository;

import com.object173.geotwitter.server.entity.Relation;
import com.object173.geotwitter.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    @Query("SELECT relation FROM Relation relation where (relation.user1.id = :userId AND relation.user2.id = :contactId) OR " +
            "(relation.user1.id = :contactId AND relation.user2.id = :userId)")
    Relation findRelation(@Param("userId") long userId, @Param("contactId") long contactId);

    @Query("SELECT relation FROM Relation relation where (relation.user1.id = :userId OR relation.user2.id = :userId) AND " +
            " NOT (relation.contact1 = false AND relation.contact2 = false)")
    List<Relation> findAllRelation(@Param("userId") long userId);

    @Query("SELECT relation FROM Relation relation where (relation.user1 = :user1 AND relation.user2 = :user2) OR " +
            "(relation.user1 = :user2 AND relation.user2 = :user1)")
    Relation getRelation(@Param("user1") User user1, @Param("user2") User user2);

    @Modifying
    @Transactional
    @Query("UPDATE Relation relation set relation.contact1 = :contact where relation.user1 = :user1 and relation.user2 = :user2")
    int setContact1(@Param("user1") User user, @Param("user2") User user2, @Param("contact") boolean contact);

    @Modifying
    @Transactional
    @Query("UPDATE Relation relation set relation.contact2 = :contact where relation.user2 = :user1 and relation.user1 = :user2")
    int setContact2(@Param("user1") User user1, @Param("user2") User user2, @Param("contact") boolean contact);
}
