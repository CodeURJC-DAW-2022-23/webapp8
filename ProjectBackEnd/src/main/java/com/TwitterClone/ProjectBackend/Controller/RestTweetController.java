package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.TweetInformation;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.Service.NotificationService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;


@RestController
public class RestTweetController {
    @Autowired
    private TweetService tweetService;
    @Autowired
    private HashtagService hashtagService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private InformationManager informationManager;
    @Autowired
    private NotificationService notificationService;

    interface Basic extends Tweet.Basic, TweetInformation.Basic, User.Basic{};

    @GetMapping("/api/tweets")
    @JsonView(Basic.class)
    public List<TweetInformation> getTweetsForAUser(@PathParam("from") int from,
                                                    @PathParam("size") int size,
                                                    HttpServletRequest request){
        User user = this.informationManager.getCurrentUser(request);
        List< Tweet > tweets = this.tweetService.findSomeRecentForUser(user.getId(), from, size);
        return this.informationManager.calculateDataOfTweet(tweets, user);
    }

    @GetMapping("/api/bookmarks")
    @JsonView(Basic.class)
    public List<TweetInformation> getTweetsForBookmarks(@PathParam("from") int from,
                                                        @PathParam("size") int size,
                                                        HttpServletRequest request){
        User user = this.informationManager.getCurrentUser(request);
        List< Tweet > tweets = this.profileService.getBookmarks(user.getId(), from, size);
        return this.informationManager.calculateDataOfTweet(tweets, user);
    }

    @GetMapping("/api/profile/{id}/tweets")
    @JsonView(Basic.class)
    public List<TweetInformation> getProfileTweets(@PathVariable Long id,
                                                   @PathParam("from") int from,
                                                   @PathParam("size") int size,
                                                   HttpServletRequest request){
        User user = this.informationManager.getCurrentUser(request);
        List< Tweet > tweets = this.tweetService.findSomeTweetOfUser(id, from, size);
        return this.informationManager.calculateDataOfTweet(tweets, user);
    }


}
