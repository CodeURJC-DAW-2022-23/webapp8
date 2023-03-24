package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.Notification;
import com.TwitterClone.ProjectBackend.Service.NotificationService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    @Autowired
    private ProfileService profileService;

    /**
     * Load more notifications using AJAX
     *
     * @param model
     * @param from
     * @param size
     * @param request
     * @return
     */
    @GetMapping("/notifications/notification")
    public String loadMoreNotifications(Model model,
                                        @Param("from") int from,
                                        @Param("size") int size,
                                        HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);
        Long idCurrentUser = currentUser.getId();
        int numNotifications = this.notificationService.countNotifications(idCurrentUser);

        if (numNotifications <= from) {
            return "redirect:/";
        }

        List<Notification> newNotifications = this.notificationService.getSomeNotificationsOfUser(idCurrentUser, from, size);
        model.addAttribute("notifications", newNotifications);

        return "notification";
    }

    /**
     * Load more mentions using AJAX
     *
     * @param model
     * @param from
     * @param size
     * @param request
     * @return
     */
    @GetMapping("/mentions/mention")
    public String loadMoreMentions(Model model,
                                   @Param("from") int from,
                                   @Param("size") int size,
                                   HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);
        Long idCurrentUser = currentUser.getId();
        int numMentions = this.notificationService.countMentions(idCurrentUser);

        if (numMentions <= from) {
            return "redirect:/";
        }

        List<Notification> newMentions = this.notificationService.getSomeMentionsOfUser(idCurrentUser, from, size);
        model.addAttribute("notifications", newMentions);

        return "notification";
    }

    /**
     * Creates a new notification when someone interacts with a tweet or user
     *
     * @param idTweet
     * @param idOwner
     * @param notificationType
     * @param request
     * @return
     */
    @GetMapping("/newNotification")
    public String createNotification(@PathParam("idTweet") Long idTweet,
                                     @PathParam("idOwner") Long idOwner,
                                     @PathParam("notificationType") String notificationType,
                                     HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);
        User owner = this.profileService.findById(idOwner).get();
        Long currentUserId = currentUser.getId();

        if (!currentUserId.equals(idOwner)) {
            this.notificationService.createNotification(idTweet, owner, currentUser, notificationType);
        }

        return "finish-request";
    }

    /**
     * Deletes a notification when someone interacts with a tweet or user
     *
     * @param idTweet
     * @param notificationType
     * @param request
     * @return
     */
    @GetMapping("/deleteNotification")
    public String deleteNotification(@PathParam("idTweet") Long idTweet,
                                     @PathParam("notificationType") String notificationType,
                                     HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);
        Long currentUserId = currentUser.getId();
        this.notificationService.deleteNotification(idTweet, currentUserId, notificationType, 0L);
        return "finish-request";
    }

    /**
     * Load some notifications of a user with AJAX
     *
     * @param from
     * @param size
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/all-notifications")
    public String getNotifications(@PathParam("from") int from,
                                   @PathParam("size") int size,
                                   Model model, HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);
        Long id = currentUser.getId();
        List<Notification> notifications = this.notificationService.getSomeNotificationsOfUser(id, from, size);
        model.addAttribute("notifications", notifications);

        return "notification";
    }

    /**
     * Load some mentions of a user with AJAX
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/mentions")
    public String getMentions(Model model, HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);
        Long id = currentUser.getId();
        List<Notification> mentions = this.notificationService.getSomeMentionsOfUser(id, 0, 10);
        model.addAttribute("notifications", mentions);

        return "notification";
    }
}
