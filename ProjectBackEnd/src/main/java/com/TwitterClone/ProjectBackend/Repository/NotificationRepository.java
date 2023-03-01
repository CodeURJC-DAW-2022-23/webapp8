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
    Optional<Notification> findById(Long id);
    List<Notification> queryFirst10ByUserToNotify(User user);
    @Query(value="SELECT * FROM \"notification\" WHERE \"type\" = 'QUOTE'",nativeQuery = true)
    List<Notification> findQuotesByUser(User user);
    List<Notification> queryFirst10ByUserToNotify(Long id);

    //Provisional, just to try if showing notifications works
    @Query(value="SELECT * FROM \"notification\"", nativeQuery = true)
    List<Notification> findAll();
    @Query(value="SELECT * FROM \"notification\" WHERE \"type\" = 'MENTION' AND \"user_to_notify_id\" = ?1", nativeQuery = true)
    List<Notification> findMentionsByUser(Long id);

    //Provisional, just to try if showing notifications works
    @Query(value="SELECT * FROM \"notification\" WHERE \"type\" = 'MENTION'", nativeQuery = true)
    List<Notification> findMentions();
}