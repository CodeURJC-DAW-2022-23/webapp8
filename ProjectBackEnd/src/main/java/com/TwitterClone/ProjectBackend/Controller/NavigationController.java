package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.*;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.TweetInformation;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.Service.NotificationService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.UserRoles;
import com.TwitterClone.ProjectBackend.userManagement.UserService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
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

    /**
     * Change from the current page to login
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "login";
    }

    /**
     * Change from the current page to signup
     * @param model
     * @param request
     * @return
     */
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
        List<Tweet> tweetList = this.tweetService.find10RecentForUser(currentUser.getId(), 0, 10);

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

        List<Notification> notifications = this.notificationService.get10NotificationsOfUser(currentUser.getId(), 0, 10);
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
        UserRoles typeUser = currentUser.getRole();
        model.addAttribute("isAdmin", typeUser.equals(UserRoles.ADMIN));

        List<Tweet> bookmarkTweetList = this.profileService.getBookmarks(currentUser.getId(), 0 , 10);
        List<TweetInformation> bookmarks= this.informationManager.calculateDataOfTweet(bookmarkTweetList);
        model.addAttribute("tweets", bookmarks);

        return "bookmarks";
    }

    /**
     * Change from the current page to the profile page
     * @return
     */
    @GetMapping("/profile")
    public String toProfile(Model model, HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);

        // Profile page shows username as page name...
        String nickname = currentUser.getNickname();
        this.informationManager.addNameToThePage(model, nickname);

        // Charge left and right bar information...
        this.informationManager.addProfileInfoToLeftBar(model, request);
        this.informationManager.addCurrentTrends(model);

        // Add necessary user data to model...
        int followersNumber = currentUser.getFollowersNumber();
        model.addAttribute("followersNumber", followersNumber);

        int followedNumber = currentUser.getFollowedNumber();
        model.addAttribute("followedNumber", followedNumber);

        List<Tweet> tweetList = this.tweetService.find10(currentUser.getId());
        List<TweetInformation> tweets = this.informationManager.calculateDataOfTweet(tweetList);
        model.addAttribute("tweets", tweets);

        model.addAttribute("user", currentUser);

        return "profile";
    }

    /**
     * Change from the current page to the user related people page
     * @return
     */
    @GetMapping("/follow")
    public String toRelatedPeople(Model model, HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);

        String nickname = currentUser.getNickname();
        String namePage = "People related to " + nickname;
        this.informationManager.addNameToThePage(model, namePage);

        this.informationManager.addProfileInfoToLeftBar(model, request);
        this.informationManager.addCurrentTrends(model);

        Long currentUserId = currentUser.getId();
        List<User> followers = profileService.getFollowers(currentUserId);
        List<User> followed = profileService.getFollowed(currentUserId);
        model.addAttribute("followers", followers);
        model.addAttribute("followed", followers);


        model.addAttribute("user", currentUser);

        return "follow";
    }

    /**
     * Change from the current page to the edit profile page
     * @return
     */
    @GetMapping("/edit-profile")
    public String toEditProfile(Model model, HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);

        this.informationManager.addNameToThePage(model, "Edit profile");
        model.addAttribute("user", currentUser);

        return "edit-profile";
    }

    /**
     * Change from the current page to the write tweet page
     * @return
     */
    @GetMapping("/write-tweet")
    public String toWriteTweet(Model model) {
        model.addAttribute("type", "new");
        return "write-tweet";
    }

    /**
     * Change the current page to the reply a tweet
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/write-tweet/comment/{id}")
    public String toReplyTweet(Model model,
                               @PathVariable("id") Long id)  {
        Optional<Tweet> tweet = this.tweetService.findById(id);
        Tweet tweetToReply = tweet.get();
        model.addAttribute("tweet", tweetToReply);
        model.addAttribute("type", "reply");

        return "write-tweet";
    }

    /**
     * Change from the current page to the error page
     * @return
     */
    @GetMapping("/error")
    public String toError() {
        return "error";
    }

    @GetMapping("/dashboard")
    public String toDashboard(Model model, HttpServletRequest request){
        this.informationManager.addNameToThePage(model,"Dashboard");
        this.informationManager.addProfileInfoToLeftBar(model,request);
        this.informationManager.addCurrentTrends(model);
        List<User> users = this.profileService.getVerified(0,10);
        model.addAttribute("verified", users);
        users = this.profileService.getBanned(0,10);
        model.addAttribute("banned", users);
        users = this.profileService.getToVerified(0,10);
        model.addAttribute("toVerify",users);
        this.informationManager.addStatistics(model);
        return "admin-dashboard";
    }
}
