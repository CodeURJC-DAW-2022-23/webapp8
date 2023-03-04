package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.Notification;
import com.TwitterClone.ProjectBackend.Service.NotificationService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * This class is on charge of controlling all request from the view regarding the notifications
 */

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private InformationManager informationManager;

    @GetMapping("/notifications/notification")
    public String loadMoreNotifications(Model model,
                                        @Param("from") int from,
                                        @Param("size") int size,
                                        HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);
        Long idCurrentUser = currentUser.getId();
        List<Notification> newNotifications = this.notificationService.get10NotificationsOfUser(idCurrentUser, from, size);
        model.addAttribute("notifications", newNotifications);

        return "notification";
    }

    @GetMapping("/mentions/mention")
    public String loadMoreMentions(Model model,
                                        @Param("from") int from,
                                        @Param("size") int size,
                                        HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);
        Long idCurrentUser = currentUser.getId();
        List<Notification> newMentions = this.notificationService.get10MentionsOfUser(idCurrentUser, from, size);
        model.addAttribute("notifications", newMentions);

        return "notification";
    }

    @GetMapping("/newNotification")
    public String createNotification(@PathParam("idTweet") Long idTweet,
                                     @PathParam("idOwner") Long idOwner,
                                     @PathParam("notificationType") String notificationType,
                                   HttpServletRequest request){
        User currentUser = this.informationManager.getCurrentUser(request);
        Long currentUserId = currentUser.getId();
        if (!currentUserId.equals(idOwner)){
            this.notificationService.createNotification(idTweet, idOwner, currentUser, notificationType);
        }
        return "finish-request";
    }

    @GetMapping("/deleteNotification")
    public String deleteNotification(@PathParam("idTweet") Long idTweet,
                                     @PathParam("notificationType") String notificationType,
                                     HttpServletRequest request){
        User currentUser = this.informationManager.getCurrentUser(request);
        Long currentUserId = currentUser.getId();
        this.notificationService.deleteNotification(idTweet, currentUserId, notificationType);
        return "finish-request";
    }

    @GetMapping("/all-notifications")
    public String getNotifications(@PathParam("from") int from,
                                   @PathParam("size") int size,
                                   Model model, HttpServletRequest request){
        User currentUser = this.informationManager.getCurrentUser(request);
        Long id = currentUser.getId();
        List<Notification> notifications = this.notificationService.get10NotificationsOfUser(id, from, size);
        model.addAttribute("notifications", notifications);

        return "notification";
    }

    @GetMapping("/mentions")
    public String getMentions(Model model, HttpServletRequest request){
        User currentUser = this.informationManager.getCurrentUser(request);
        Long id = currentUser.getId();
        List<Notification> mentions = this.notificationService.get10MentionsOfUser(id, 0, 10);
        model.addAttribute("notifications", mentions);

        return "notification";
    }
}
