package com.object173.geotwitter.server.repository;


import com.object173.geotwitter.server.entity.Dialog;
import com.object173.geotwitter.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DialogRepository extends JpaRepository<Dialog, Long> {

    @Query("SELECT dialog FROM Dialog dialog where (dialog.user1 = :user AND dialog.user2 = :companion) OR " +
            "(dialog.user2 = :user AND dialog.user1 = :companion)")
    Dialog getDialog(@Param("user") User user, @Param("companion") User companion);

    @Query("SELECT dialog FROM Dialog dialog where dialog.user1 = :user OR dialog.user2 = :user")
    List<Dialog> getDialogList(@Param("user") User user);
}
