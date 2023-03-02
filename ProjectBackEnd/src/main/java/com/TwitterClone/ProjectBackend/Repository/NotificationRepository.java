package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Notification;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(value = "SELECT * FROM notification WHERE user_to_notify_id = ?1 ORDER BY date DESC",nativeQuery = true)
    List<Notification> findNotifications(long user_id);
    @Query(value = "SELECT * FROM notification WHERE user_to_notify_id = ?1 AND type='COMMENT' ORDER BY date DESC",nativeQuery = true)
    List<Notification> findQuotes(long user_id);
}