package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Notification;
import com.TwitterClone.ProjectBackend.Repository.NotificationRepository;
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

    //Needs to be changed by a query to select active-user's notifications
    public List<Notification> getNotifications() {
        return this.notificationRepository.findAll();
    }

    //Needs to be changed by a query to select active-user's mentions
    public List<Notification> getMentions(){
        return null;
    }
}
