package com.TwitterClone.ProjectBackend.Model.MustacheObjects;

import com.TwitterClone.ProjectBackend.Model.Trend;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.TwitterClone.ProjectBackend.userManagement.UserRoles;
import com.TwitterClone.ProjectBackend.userManagement.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manage all the recurrent information needed by the controllers
 */
@Component
@NoArgsConstructor
public class InformationManager {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private HashtagService hashtagService;
    @Autowired
    private TweetService tweetService;
    @Autowired
    private UserService userService;

    /**
     * Add the current user to the left-bar
     * @param model
     * @param request
     */
    public void addProfileInfoToLeftBar(Model model, HttpServletRequest request) {
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
        model.addAttribute("type", currentUser.getType());

        if (currentUser.getType().equals("PRIVATE")) {
            model.addAttribute("private-acount", currentUser.getType());
        }
    }

    /**
     * Add name to the current page
     * @param model
     * @param namePage
     */
    public void addNameToThePage(Model model, String namePage) {
        model.addAttribute("namePage", namePage);
    }

    /**
     * Prepare the list with the Tweets to show at mustache
     * @param tweets
     */
    public List<TweetInformation> calculateDataOfTweet(List<Tweet> tweets, User currentUser) {
        List<TweetInformation> tweetsInfo = new ArrayList<>();

        for(Tweet tweet : tweets) {
            TweetInformation currentTweetInformation = new TweetInformation();
            currentTweetInformation.setTweet(tweet);
            currentTweetInformation.setNumLikes(this.tweetService.getLikesOfTweet(tweet.getId()));
            currentTweetInformation.setNumComments(this.tweetService.getCommentsOfTweet(tweet.getId()));
            currentTweetInformation.setNumRetweets(this.tweetService.getRetweetsOfTweet(tweet.getId()));

            this.checkUserStateAboutTweets(tweet, currentUser, currentTweetInformation);

            tweetsInfo.add(currentTweetInformation);
        }

        return tweetsInfo;
    }

    /**
     * Check the user status about the tweets to be showed
     * @param tweet
     * @param currentUser
     * @param currentTweetInformation
     */
    private void checkUserStateAboutTweets(Tweet tweet,
                                           User currentUser, TweetInformation currentTweetInformation) {
        currentTweetInformation.setAuthorised(this.isAuthorised(currentUser, tweet));
        if (this.tweetService.isRetweeted(currentUser, tweet)) {
            currentTweetInformation.setColorRetweet("green-0");
        } else {
            currentTweetInformation.setColorRetweet("gray-4");
        }
        if (this.tweetService.isLiked(currentUser, tweet)) {
            currentTweetInformation.setColorLike("red-0");
        } else {
            currentTweetInformation.setColorLike("gray-4");
        }
        if (this.tweetService.isBookmarked(currentUser, tweet)) {
            currentTweetInformation.setColorBookmark("primary");
        } else {
            currentTweetInformation.setColorBookmark("gray-4");
        }
    }

    /**
     * Check if the user is authorised
     * @param currentUser
     * @param tweet
     * @return
     */
    private boolean isAuthorised(User currentUser, Tweet tweet) {
        if (currentUser == null) {
            return false;
        }

        boolean condition1 = currentUser.getId().equals(tweet.getUser().getId());
        boolean condition2 = currentUser.getRole().equals(UserRoles.ADMIN);
        return condition2 || condition1;
    }

    /**
     * Obtain the current User in the session
     * @param request
     * @return
     */
    public User getCurrentUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            return null;
        }

        Optional<User> currentSession = this.profileService.findByUsername(principal.getName());
        return currentSession.get();
    }

    /**
     * Add to the template the current trends
     * @param model
     */
    public void addCurrentTrends(Model model) {

        List<Trend> trends = this.hashtagService.getCurrentTrends(0,5);

        model.addAttribute("trends", trends);
    }

    /**
     * Add charts to the dashboard page
     * @param model
     */
    public void addStatistics(Model model) {
        List<Tuple> statics = this.profileService.getStatics();
        String[] amounts = new String[statics.size()];
        String[] dates = new String[statics.size()];

        for (int i = 0;i<statics.size();i++){
            dates[i] = statics.get(i).get("join_date").toString();
            amounts[i] = statics.get(i).get("new_people").toString();
        }

        model.addAttribute("amounts",amounts);
        model.addAttribute("dates",dates);
    }

    /**
     * Add recommended users
     * @param model
     * @param request
     */
    public void addRecommended(Model model, HttpServletRequest request){
        User currentUser = this.getCurrentUser(request);

        if (currentUser == null) {
            return;
        }

        List<User> recommended = this.userService.getRecommendedUsers(currentUser.getId());
        model.addAttribute("usersToFollow",recommended);
    }
}
