package com.TwitterClone.ProjectBackend.Controller.RestController;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    //It makes an error in console but works
    @PostMapping("/api/tweets/reply-tweet/{idTweetReplied}")
    @JsonView(Basic.class)
    public Tweet postTweet(@RequestParam("text") String tweet_info,
                           @RequestParam("files") MultipartFile[] tweet_files,
                           @PathVariable("idTweetReplied") Long idTweetReplied,
                           HttpServletRequest request) throws IOException {
        Blob[] files = this.informationManager.manageImages(tweet_files);
        User currentUser = this.informationManager.getCurrentUser(request);
        Long userId = currentUser.getId();
        Tweet newTweet = this.tweetService.createTweet(tweet_info, files, userId);
        this.informationManager.processTextTweet(tweet_info, newTweet, currentUser);

        Tweet tweetReplied = this.tweetService.findById(idTweetReplied).orElse(null);
        User user_reply = tweetReplied.getUser();

        tweetService.addComment(idTweetReplied, newTweet);

        if (!userId.equals(user_reply.getId())) {
            this.notificationService
                    .createNotification(newTweet.getId(), user_reply, currentUser, "MENTION");
        }

        return newTweet;
    }

    //It makes an error in console but works
    @PostMapping("/api/tweets/new-tweet")
    @JsonView(Basic.class)
    public Tweet postTweet(@RequestParam("text") String tweet_info,
                           @RequestParam("files") MultipartFile[] tweet_files,
                           HttpServletRequest request) throws IOException {
        Blob [] files = this.informationManager.manageImages(tweet_files);
        User currentUser = this.informationManager.getCurrentUser(request);
        Long userId = currentUser.getId();
        Tweet newTweet = this.tweetService.createTweet(tweet_info, files, userId);
        this.informationManager.processTextTweet(tweet_info, newTweet, currentUser);

        return newTweet;
    }

    @DeleteMapping("/api/tweet/delete/{id}")
    @JsonView(Basic.class)
    public Tweet deleteTweet(@PathVariable("id") Long id) {
        Tweet tweet = this.tweetService.findById(id).orElse(null);
        if ((tweet != null)){
            this.tweetService.deleteTweet(tweet);
            return tweet;
        }
        else{
            return null;
        }
    }

    @PutMapping("/api/tweet/like/{id}")
    @JsonView(Basic.class)
    public TweetInformation toggleLike(@PathVariable("id") Long id,
                             HttpServletRequest request){
        Tweet tweet = this.tweetService.findById(id).get();
        User currentUser = this.informationManager.getCurrentUser(request);
        tweetService.toggleLike(currentUser, tweet);

        List list = new ArrayList();
        list.add(tweet);
        List<TweetInformation> tweetInformation = this.informationManager.calculateDataOfTweet(list, currentUser);
        return tweetInformation.get(0);
    }

    @PutMapping("/api/tweet/retweet/{id}")
    @JsonView(Basic.class)
    public TweetInformation toggleRetweet(@PathVariable("id") Long id,
                                       HttpServletRequest request){
        Tweet tweet = this.tweetService.findById(id).get();
        User currentUser = this.informationManager.getCurrentUser(request);
        tweetService.toggleRetweet(currentUser, tweet);

        List<Tweet> list = new ArrayList();
        list.add(tweet);
        List<TweetInformation> tweetInformation = this.informationManager.calculateDataOfTweet(list, currentUser);
        return tweetInformation.get(0);
    }

    @PutMapping("/api/tweet/bookmark/{id}")
    @JsonView(Basic.class)
    public List<TweetInformation> toggleBookmark(@PathVariable("id") Long id,
                                                 HttpServletRequest request){
        Tweet tweet = this.tweetService.findById(id).get();
        User currentUser = this.informationManager.getCurrentUser(request);
        this.tweetService.toggleBookmark(currentUser, tweet);
        List<Tweet> bookmarks = new ArrayList<>();
        bookmarks.add(tweet);
        return this.informationManager.calculateDataOfTweet(bookmarks, currentUser);
    }
}
