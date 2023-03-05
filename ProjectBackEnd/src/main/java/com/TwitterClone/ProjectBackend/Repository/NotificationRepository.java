package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Notification;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This Repository is the connection to DB for Notification Entity
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    /**
     * This Query returns a List of Notification of a user ordered by the date when they appeared
     * @param user_id
     * @param init
     * @param size
     * @return
     */
    @Query(value = "SELECT * FROM notification WHERE user_to_notify_id = ?1 ORDER BY date DESC LIMIT ?2,?3",nativeQuery = true)
    List<Notification> findNotifications(long user_id, int init, int size);

    /**
     * This Query returns a List of Notification that are Mentions of a user ordered by the date when they appeared
     * @param user_id
     * @param init
     * @param size
     * @return
     */
    @Query(value = "SELECT * FROM notification WHERE user_to_notify_id = ?1 AND type='MENTION' ORDER BY date DESC LIMIT ?2,?3",nativeQuery = true)
    List<Notification> findMentions(long user_id, int init, int size);

    /**
     * This Query returns the required Notification because when delete we do not have the id
     * @param userWhoNotifiesId
     * @param tweetId
     * @param type
     * @return
     */
    @Query(value = "SELECT * FROM notification WHERE user_who_notifies_id = ?1 AND tweet_trigger_id = ?2 AND type = ?3",nativeQuery = true)
    Optional<Notification> findSpecificNotification(long userWhoNotifiesId,long tweetId, String type);

    @Query(value = "SELECT COUNT(*) FROM notification WHERE user_to_notify_id = ?1", nativeQuery = true)
    int countNotifications(Long idCurrentUser);

    @Query(value = "SELECT COUNT(*) FROM notification WHERE user_to_notify_id = ?1 AND type='MENTION'",nativeQuery = true)
    int countMentions(Long idCurrentUser);
}