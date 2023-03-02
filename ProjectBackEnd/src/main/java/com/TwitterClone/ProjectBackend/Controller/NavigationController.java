package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.*;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.Service.NotificationService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.UserService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * This class is on charge of controlling the navigation through the website.
 */

@Controller
public class NavigationController {

    @Autowired
    private HashtagService hashtagService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private TweetService tweetService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "/";
    }

    /**
     * Change from the current page to the home page
     * @param model
     * @return
     */

    @GetMapping("/home")
    public String toHome(Model model, HttpServletRequest request) {

        String namePage = "Home";
        model.addAttribute("namePage", namePage);

        this.addProfileInfoToLeftBar(model, request);
        this.addCurrentTrends(model);

        Principal principal = request.getUserPrincipal();
        Optional<User> currentSession = this.profileService.findByUsername(principal.getName());
        User currentUser = currentSession.get();

        List<Tweet> tweets = this.tweetService.find10RecentForUser(currentUser.getId());

        model.addAttribute("tweets", tweets);


        return "home";
    }

    /**
     * Change from the current page to the explore page
     * @param model
     * @return
     */
    @GetMapping("/explore")
    public String toExplore(Model model, HttpServletRequest request) {

        String namePage = "Explore";
        model.addAttribute("namePage", namePage);
        model.addAttribute("isExplorePage", true);

        this.addCurrentTrends(model);
        this.addProfileInfoToLeftBar(model, request);

        List<Trend> trends = this.hashtagService.getCurrentTrends(0,10);
        model.addAttribute("trends", trends);

        return "explore";
    }

    /**
     * Change from the current page to the notifications page
     * @param model
     * @return
     */
    @GetMapping("/notifications")
    public String toNotifications(Model model, HttpServletRequest request) {

        String namePage = "Notifications";
        model.addAttribute("namePage", namePage);

        this.addCurrentTrends(model);
        this.addProfileInfoToLeftBar(model, request);

        List<Notification> notifications = this.notificationService.getNotifications();
        model.addAttribute("notifications", notifications);

        return "notifications";
    }

    /**
     * Change from the current page to the bookmarks page
     * @param model
     * @return
     */
    @GetMapping("/bookmarks")
    public String toBookmark(Model model, HttpServletRequest request) {

        String namePage = "Bookmarks";
        model.addAttribute("namePage", namePage);

        this.addCurrentTrends(model);
        this.addProfileInfoToLeftBar(model, request);

        Principal principal = request.getUserPrincipal();
        Optional<User> currentSession = this.profileService.findByUsername(principal.getName());
        User currentUser = currentSession.get();

        List<Tweet> bookmarks = this.profileService.getBookmarks(currentUser.getId(), 0, 10);
        model.addAttribute("bookmarks", bookmarks);

        return "bookmarks";
    }

    /**
     * Change from the current page to the profile page
     * @return
     */
    @GetMapping("/profile")
    public String toProfile() {
        return "profile";
    }

    /**
     * Change from the current page to the write tweet page
     * @return
     */
    @GetMapping("/write-tweet")
    public String toWriteTweet() {
        return "write-tweet";
    }

    /**
     * Cahnge from the current page to the error page
     * @return
     */
    @GetMapping("/error")
    public String toError() {
        return "error";
    }

    /**
     * Add to the template the current trends
     * @param model
     */
    private void addCurrentTrends(Model model) {

        List<Trend> trends = this.hashtagService.getCurrentTrends(0,5);

        model.addAttribute("trends", trends);
    }

    /**
     * Add the current user to the left-bar
     * @param model
     * @param request
     */
    private void addProfileInfoToLeftBar(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        if(principal == null) {
            model.addAttribute("isLogged", false);
            return;
        }

        Optional<User> currentSession = this.profileService.findByUsername(principal.getName());
        User currentUser = currentSession.get();

        if (currentUser == null) {
            return;
        }

        model.addAttribute("isLogged", true);
        model.addAttribute("id", currentUser.getId());
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("nickname", currentUser.getNickname());

        if (currentUser.getType().equals("PRIVATE")) {
            model.addAttribute("private-acount", currentUser.getType());
        }
    }
}
