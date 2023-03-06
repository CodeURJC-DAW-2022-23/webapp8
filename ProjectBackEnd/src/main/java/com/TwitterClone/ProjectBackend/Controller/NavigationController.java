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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
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
        return "redirect:/";
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

        List<TweetInformation> tweets = this.informationManager.calculateDataOfTweet(tweetList, currentUser);
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

        this.informationManager.addRecommended(model,request);

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
        List<TweetInformation> bookmarks= this.informationManager.calculateDataOfTweet(bookmarkTweetList, currentUser);
        model.addAttribute("tweets", bookmarks);

        return "bookmarks";
    }

    /**
     * Change from the current page to the profile page
     * @return
     */
    @GetMapping("/profile/{id}")
    public String toProfile(Model model,
                            HttpServletRequest request,
                            @PathVariable Long id) {
        User profileUser = this.profileService.findById(id).get();
        User currentUser = this.informationManager.getCurrentUser(request);
        model.addAttribute("isYourProfile", id.equals(currentUser.getId()));
        model.addAttribute("isFollowed", this.profileService.isFollowedBy(profileUser, currentUser));
        model.addAttribute("isBanned", profileUser.isEnabled());

        // Profile page shows username as page name...
        String nickname = profileUser.getNickname();
        this.informationManager.addNameToThePage(model, nickname);

        // Charge left and right bar information...
        this.informationManager.addProfileInfoToLeftBar(model, request);
        this.informationManager.addCurrentTrends(model);

        // Add necessary user data to model...
        int followersNumber = profileUser.getFollowersNumber();
        model.addAttribute("followersNumber", followersNumber);

        int followedNumber = profileUser.getFollowedNumber();
        model.addAttribute("followedNumber", followedNumber);

        List<Tweet> tweetList = this.tweetService.find10(profileUser.getId(),0, 10);
        List<TweetInformation> tweets = this.informationManager.calculateDataOfTweet(tweetList, currentUser);
        model.addAttribute("tweets", tweets);

        model.addAttribute("user", profileUser);

        //Hide Go To Dashboard button
        if (currentUser.getRole() == UserRoles.ADMIN){
            model.addAttribute("isAdmin",true);
        }else{
            model.addAttribute("isAdmin",false);
        }

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
        List<User> followed = profileService.getFollowed(currentUserId, 0, 10);
        model.addAttribute("follows", followed);


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
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());

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

    @GetMapping("/verify/{id}")
    public String verify(@PathVariable Long id){
        User user = this.profileService.findById(id).get();
        user.setType("VERIFIED");
        this.profileService.updateUserBan(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/unverify/{id}")
    public String unverify(@PathVariable Long id){
        User user = this.profileService.findById(id).get();
        user.setType("PUBLIC");
        this.profileService.updateUserBan(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/users/{userId}/recommended")
    public String getRecommendedUsers(@PathVariable Long userId, Model model) {
        List<User> recommendedUsers = userService.getRecommendedUsers(userId); // Obtener 1 usuario recomendados para el usuario con el ID especificado
        model.addAttribute("recommendedUsers", recommendedUsers);
        return "recommended-users"; // Devolver el nombre de la vista para mostrar la lista de usuarios recomendados
    }

    @GetMapping("/tweet/{id}")
    public String toShowTweet(@PathVariable Long id, Model model, HttpServletRequest request){
        this.informationManager.addCurrentTrends(model);
        this.informationManager.addNameToThePage(model,"Tweet");
        this.informationManager.addProfileInfoToLeftBar(model,request);

        Tweet tweet = this.tweetService.findById(id).get();
        List<Tweet> tweets = new LinkedList<>();
        tweets.add(tweet);
        User currentUser = this.informationManager.getCurrentUser(request);
        List<TweetInformation> tweetInformation = this.informationManager.calculateDataOfTweet(tweets,currentUser);
        model.addAttribute("tweet", tweetInformation);

        List<Tweet> replies = this.tweetService.getComments(id,0,10);
        tweetInformation = this.informationManager.calculateDataOfTweet(replies,currentUser);
        model.addAttribute("tweets",tweetInformation);

        return "show-tweet";
    }
}
