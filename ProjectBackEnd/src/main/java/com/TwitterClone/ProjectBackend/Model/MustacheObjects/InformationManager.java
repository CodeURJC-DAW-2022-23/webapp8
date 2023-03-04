package com.TwitterClone.ProjectBackend.Model.MustacheObjects;

import com.TwitterClone.ProjectBackend.Model.Trend;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@NoArgsConstructor
public class InformationManager {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private HashtagService hashtagService;
    @Autowired
    private TweetService tweetService;

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
    public List<TweetInformation> calculateDataOfTweet(List<Tweet> tweets) {
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
    public User getCurrentUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
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
}
