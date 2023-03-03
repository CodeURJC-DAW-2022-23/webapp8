package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.*;
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
        this.addNameToThePage(model,"Home");
        this.addProfileInfoToLeftBar(model, request);
        this.addCurrentTrends(model);

        User currentUser = this.getCurrentUser(request);
        List<Tweet> tweetList = this.tweetService.find10RecentForUser(currentUser.getId());
        List<TweetInformation> tweets = this.calculateDataOfTweet(model, tweetList);
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

        this.addNameToThePage(model, "Explore");
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

        this.addNameToThePage(model, "Notifications");

        this.addCurrentTrends(model);
        this.addProfileInfoToLeftBar(model, request);
        User currentUser = getCurrentUser(request);

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

        this.addNameToThePage(model, "Bookmarks");

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
     * Add name to the current page
     * @param model
     * @param namePage
     */
    private void addNameToThePage(Model model, String namePage) {
        model.addAttribute("namePage", namePage);
    }

    /**
     * Prepare the list with the Tweets to show at mustache
     * @param model
     * @param tweets
     */
    private List<TweetInformation> calculateDataOfTweet(Model model, List<Tweet> tweets) {
        List<TweetInformation> tweetsInfo = new ArrayList<>();

        for(Tweet tweet : tweets) {
            TweetInformation currentTweetInformation = new TweetInformation();
            currentTweetInformation.setTweet(tweet);
            currentTweetInformation.setNumLikes(this.tweetService.getLikesOfTweet(tweet.getId()));
            currentTweetInformation.setNumComments(this.tweetService.getCommentsOfTweet(tweet.getId()));
            currentTweetInformation.setNumRetweets(this.tweetService.getRetweetsOfTweet(tweet.getId()));
            tweetsInfo.add(currentTweetInformation);
        }

        return tweetsInfo;
    }

    /**
     * Obtain the current User in the session
     * @param request
     * @return
     */
    private User getCurrentUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Optional<User> currentSession = this.profileService.findByUsername(principal.getName());
        return currentSession.get();
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
