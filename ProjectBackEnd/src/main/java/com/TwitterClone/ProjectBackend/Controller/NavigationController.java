package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.*;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.TweetInformation;
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
import java.util.ArrayList;
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
    @Autowired
    private InformationManager informationManager;

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
        this.informationManager.addNameToThePage(model,"Home");
        this.informationManager.addProfileInfoToLeftBar(model, request);
        this.informationManager.addCurrentTrends(model);

        User currentUser = this.informationManager.getCurrentUser(request);
        List<Tweet> tweetList = this.tweetService.find10RecentForUser(currentUser.getId());
        List<TweetInformation> tweets = this.informationManager.calculateDataOfTweet(model, tweetList);
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

        this.informationManager.addNameToThePage(model, "Explore");
        model.addAttribute("isExplorePage", true);

        this.informationManager.addCurrentTrends(model);
        this.informationManager.addProfileInfoToLeftBar(model, request);

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

        this.informationManager.addNameToThePage(model, "Notifications");

        this.informationManager.addCurrentTrends(model);
        this.informationManager.addProfileInfoToLeftBar(model, request);
        User currentUser = this.informationManager.getCurrentUser(request);

        List<Notification> notifications = this.notificationService.getNotificationsOfUser(currentUser.getId());
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

        this.informationManager.addNameToThePage(model, "Bookmarks");

        this.informationManager.addCurrentTrends(model);
        this.informationManager.addProfileInfoToLeftBar(model, request);

        User currentUser = this.informationManager.getCurrentUser(request);

        List<Tweet> bookmarkTweetList = this.profileService.getBookmarks(currentUser.getId());
        List<TweetInformation> bookmarks= this.informationManager.calculateDataOfTweet(model, bookmarkTweetList);
        model.addAttribute("tweets", bookmarks);

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

}
