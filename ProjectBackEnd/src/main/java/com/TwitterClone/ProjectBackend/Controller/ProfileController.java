package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Service.NotificationService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private InformationManager informationManager;
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/profile/edit-profile")
    public String editProfile(HttpServletRequest request,
                              @RequestParam MultipartFile banner,
                              @RequestParam MultipartFile profile,
                              @RequestParam String nickname,
                              @RequestParam String biography) throws IOException {
        User currentUser = this.informationManager.getCurrentUser(request);
        this.profileService.uploadProfile(currentUser, banner, profile, nickname, biography);
        String id = currentUser.getId().toString();
        return "redirect:/profile/" + id;
    }

    @GetMapping("/followed/{username}/{from}/{size}")
    public String getFollowed(Model model,
                              @PathVariable int from,
                              @PathVariable int size,
                              @PathVariable String username) {
        User user = this.profileService.findByUsername(username).get();
        long countFollowed = this.profileService.countFollowed(user.getId());

        if (countFollowed <= from) {
            return "redirect:/";
        }

        List<User> followed = profileService.getFollowed(user.getId(), from, size);
        model.addAttribute("follows", followed);

        return "follow-content";
    }

    @GetMapping("/followers/{username}/{from}/{size}")
    public String getFollowers(Model model,
                               @PathVariable int from,
                               @PathVariable int size,
                               @PathVariable String username) {
        User user = this.profileService.findByUsername(username).get();
        long countFollowers = this.profileService.countFollowers(user.getId());

        if (countFollowers <= from) {
            return "redirect:/";
        }

        List<User> followed = profileService.getFollowers(user.getId(), from, size);
        model.addAttribute("follows", followed);

        return "follow-content";
    }

    @GetMapping("/ban/{id}")
    public String ban(@PathVariable Long id){
        User user = this.profileService.findById(id).get();
        user.setType("BANNED");
        user.setEnabled(false);
        this.profileService.updateUserBan(user);
        return "redirect:/profile/" + id.toString();
    }

    @GetMapping("/unban/{id}")
    public String unban(@PathVariable Long id){
        User user = this.profileService.findById(id).get();
        user.setType("PUBLIC");
        user.setEnabled(true);
        this.profileService.updateUserBan(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/toggleFollow/{id}")
    public String toggleFollow(@PathVariable Long id,
                         HttpServletRequest request){
        User profileUser = this.profileService.findById(id).get();
        User currentUser = this.informationManager.getCurrentUser(request);
        boolean hasFollowed = this.profileService.toggleFollow(profileUser, currentUser);
        if (hasFollowed){
            this.notificationService.createNotification(null, profileUser, currentUser, "FOLLOW");
        } else {
            this.notificationService.deleteNotification(null,
                    currentUser.getId(), "FOLLOW", profileUser.getId());
        }
        return "redirect:/profile/" + id;
    }

}
