package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Notification;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.NotificationRepository;
import com.TwitterClone.ProjectBackend.Repository.TweetRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class contains all logic needed for the notifications
 */
@NoArgsConstructor
@AllArgsConstructor
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TweetRepository tweetRepository;

    /**
     * Get some notifications from a user
     * @param userId
     * @param init
     * @param size
     * @return
     */
    public List<Notification> getSomeNotificationsOfUser(Long userId, int init, int size) {
        List<Notification> list = this.notificationRepository.findNotifications(userId, init, size);
        return list;
    }

    /**
     * Get some mentions from a user
     * @param userId
     * @param init
     * @param size
     * @return
     */
    public List<Notification> getSomeMentionsOfUser(Long userId, int init, int size) {
        List<Notification> list = this.notificationRepository.findMentions(userId, init, size);
        return list;
    }

    /**
     * Creates a new notification due to an action with a tweet or a user
     * @param idTweet
     * @param owner
     * @param currentUser
     * @param notificationType
     */
    public Optional<Notification> createNotification(Long idTweet, User owner, User currentUser, String notificationType) {
        Tweet tweetTrigger = null;
        if(idTweet != null){
            tweetTrigger = this.tweetRepository.findById(idTweet).orElse(null);
        }

        if (owner == null){
            return Optional.empty();
        }

        Notification notification = new Notification(tweetTrigger, owner, currentUser, notificationType);
        this.notificationRepository.save(notification);
        return Optional.of(notification);
    }

    /**
     * Deletes a notification due to an action with a tweet or a user
     * @param idTweet
     * @param idCurrentUser
     * @param notificationType
     * @param idUserToNotify
     */
    public Optional<Notification> deleteNotification(Long idTweet, Long idCurrentUser, String notificationType, Long idUserToNotify) {
        Notification notification;

        if (idTweet != null){
            notification = this.notificationRepository.findSpecificNotification(idCurrentUser, idTweet, notificationType).orElse(null);
        } else {
            notification = this.notificationRepository.findFollowNotification(idCurrentUser, idUserToNotify).orElse(null);
        }

        if (notification == null){
            return Optional.empty();
        }

        this.notificationRepository.delete(notification);
        return Optional.of(notification);
    }

    /**
     * Obtain the amount of notifications from a user
     * @param idCurrentUser
     * @return
     */
    public int countNotifications(Long idCurrentUser) {
        return this.notificationRepository.countNotifications(idCurrentUser);
    }

    /**
     * Obtain the amount of mentions from a user
     * @param idCurrentUser
     * @return
     */
    public int countMentions(Long idCurrentUser) {
        return this.notificationRepository.countMentions(idCurrentUser);
    }
}
