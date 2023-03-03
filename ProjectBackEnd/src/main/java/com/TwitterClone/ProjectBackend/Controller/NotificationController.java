package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.Trend;
import com.TwitterClone.ProjectBackend.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * This class is on charge of controlling all request from the view regarding the notifications
 */

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications/notification")
    public String loadMoreNotifications(Model model,
                                        @Param("from") int from,
                                        @Param("size") int size) {
        /*List<Trend> newTrends = this.notificationService.getNotifications(from, size);
        model.addAttribute("trends", newTrends);*/

        return "notification";
    }
}
