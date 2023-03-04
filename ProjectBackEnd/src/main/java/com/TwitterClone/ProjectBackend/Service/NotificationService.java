package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Notification;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.NotificationRepository;
import com.TwitterClone.ProjectBackend.Repository.TweetRepository;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;

    public List<Notification> getNotificationsOfUser(Long userId) {
        List<Notification> list = this.notificationRepository.findNotifications(userId);
        return list;
    }

    public void createNotification(Long idTweet, Long idOwner, User currentUser, String notificationType) {
        User owner = this.userRepository.findById(idOwner).orElse(null);
        Tweet tweetTrigger = this.tweetRepository.findById(idTweet).orElse(null);
        if (owner != null && tweetTrigger != null){
            Notification notification = new Notification(tweetTrigger, owner, currentUser, notificationType);
            this.notificationRepository.save(notification);
        }
    }

    public void deleteNotification(Long idNotification) {
        Notification notification = this.notificationRepository.getReferenceById(idNotification);
        this.notificationRepository.delete(notification);
    }
}
