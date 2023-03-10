package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.TweetInformation;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.Service.NotificationService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

/**
 * Manage all the petitions relation with tweets
 */
@Controller
public class TweetController {
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

    /**
     * Ask the database for more tweets for the home page
     * @param model
     * @param from
     * @param size
     * @return
     */
    @GetMapping("/home/tweets")
    public String loadMoreTweetsForHome(Model model,
                                        HttpServletRequest request,
                                        @PathParam("from") int from,
                                        @PathParam("size") int size) {
        User currentUser = this.informationManager.getCurrentUser(request);
        int numTweetsForUser = this.profileService.countTweetsForUser(currentUser.getId());

        if (numTweetsForUser <= from) {
            return "redirect:/";
        }

        List<Tweet> newTweets =
                this.tweetService.findSomeRecentForUser(currentUser.getId(), from, size);
        List<TweetInformation> tweets = this.informationManager.calculateDataOfTweet(newTweets, currentUser);
        model.addAttribute("tweets", tweets);

        return "tweet";
    }
    /**
     * Ask the database for more tweets for the bookmarks page
     * @param model
     * @param from
     * @param size
     * @return
     */
    @GetMapping("/bookmarks/tweets")
    public String loadMoreTweetsForBookmarks(Model model,
                                             HttpServletRequest request,
                                             @PathParam("from") int from,
                                             @PathParam("size") int size) {
        User currentUser = this.informationManager.getCurrentUser(request);
        int numBookmarks = this.profileService.countBookmarks(currentUser.getId());

        if (numBookmarks <= from) {
            return "redirect:/";
        }

        List<Tweet> newTweets =
                this.profileService.getBookmarks(currentUser.getId(), from, size);
        List<TweetInformation> tweets =
                this.informationManager.calculateDataOfTweet(newTweets, currentUser);
        model.addAttribute("tweets", tweets);

        return "tweet";
    }

    /**
     * Ask the database for more tweets from the user
     * @param model
     * @param from
     * @param size
     * @return
     */
    @GetMapping("/profile/tweets/{id}")
    public String loadMoreTweetsForProfile(Model model,
                                           @PathVariable long id,
                                           @PathParam("from") int from,
                                           @PathParam("size") int size) {

        User user = this.profileService.findById(id).get();
        int countTweetsOfUser = this.profileService.countTweetsOfUser(user.getId());

        if (countTweetsOfUser <= from) {
            return "redirect:/";
        }

        List<Tweet> newTweets = this.tweetService.findSomeTweetOfUser(id, from, size);
        List<TweetInformation> tweets = this.informationManager.calculateDataOfTweet(newTweets, user);
        model.addAttribute("tweets", tweets);

        return "tweet";
    }


    /**
     * Add a new tweet from the trigger user to the database
     * @param tweet_info
     * @param tweet_files
     * @return
     * @throws IOException
     */
    @PostMapping("/tweets/new-tweet")
    public String postTweet(@RequestParam String tweet_info,
                            @RequestParam MultipartFile [] tweet_files,
                            HttpServletRequest request) throws IOException {
        Blob [] files = this.manageImages(tweet_files);
        User currentUser = this.informationManager.getCurrentUser(request);
        Long userId = currentUser.getId();
        Tweet newTweet = this.tweetService.createTweet(tweet_info, files, userId);
        processTextTweet(tweet_info, newTweet, currentUser);

        return "redirect:/home";
    }

    /**
     * Creates a reply of a tweet
     * @param tweet_info
     * @param tweet_files
     * @param id
     * @param idTweetReplied
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/tweets/reply-tweet/{idTweetReplied}")
    public String postTweet(@RequestParam String tweet_info,
                            @RequestParam MultipartFile [] tweet_files,
                            @RequestParam("user_reply") Long id,
                            @PathVariable("idTweetReplied") Long idTweetReplied,
                            HttpServletRequest request) throws IOException {
        Blob [] files = this.manageImages(tweet_files);
        User currentUser = this.informationManager.getCurrentUser(request);
        Long userId = currentUser.getId();
        Tweet newTweet = this.tweetService.createTweet(tweet_info, files, userId);
        processTextTweet(tweet_info, newTweet, currentUser);

        Optional<User> user_owner = this.profileService.findById(id);
        User user_reply = user_owner.get();
        tweetService.addComment(idTweetReplied, newTweet);

        if (!userId.equals(user_reply.getId())) {
            this.notificationService
                    .createNotification(newTweet.getId(), user_reply, currentUser, "MENTION");
        }

        return "redirect:/home";
    }

    /**
     * Process all the image files from the input
     * @param tweet_files
     * @return
     * @throws IOException
     */
    private Blob[] manageImages(MultipartFile [] tweet_files) throws IOException {
        Blob [] files = new Blob[4];
        if(!tweet_files[0].isEmpty()){
            for (int index = 0; (index < tweet_files.length) && (index < 4); index++) {
                files[index] = BlobProxy
                        .generateProxy(tweet_files[index]
                                .getInputStream(), tweet_files[index]
                                .getSize());
            }
        }

        return files;
    }

    /**
     * Process the text of a tweet to analyze if exist a hashtag or a mention
     * @param text
     * @param tweet
     * @param currentUser
     */
    private void processTextTweet(String text, Tweet tweet, User currentUser){
        String [] splitText = text.split(" ");

        for(String segment : splitText){

            if (segment.startsWith("#")) {
                this.processHashtag(segment, tweet);
            }

            if (segment.startsWith("@")) {
                this.processMention(segment, tweet, currentUser);
            }
        }
    }

    /**
     * Process a mention when it appears in a tweet
     * @param segment
     * @param tweet
     * @param currentUser
     */
    private void processMention(String segment, Tweet tweet, User currentUser) {
        String [] splitMention = segment.split("@");

        for (String mention : splitMention) {
            User userToMention = this.profileService.findByUsername(mention).orElse(null);

            if (userToMention != null && !currentUser.getId().equals(userToMention.getId())) {
                this.notificationService
                        .createNotification(
                                tweet.getId(),
                                userToMention,
                                currentUser,
                                "MENTION");
            }
        }
    }

    /**
     * Process a hashtag when it appears in a tweet
     * @param segment
     * @param firstTweet
     */
    private void processHashtag(String segment, Tweet firstTweet) {
        String [] splitHashtags = segment.split("#");

        for (String hashtag : splitHashtags) {

            if (!hashtag.equals("")) {
                hashtagService.add(hashtag, firstTweet);
            }
        }
    }

    /**
     * When the like buttons is pressed, it will check if the user is giving or removing the likes
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping("/tweet/like/{id}")
    public String toggleLike(@PathVariable("id") Long id,
                              HttpServletRequest request) throws IOException {
        Tweet tweet = this.tweetService.findById(id).get();
        User currentUser = this.informationManager.getCurrentUser(request);
        boolean hasGiveLike = tweetService.toggleLike(currentUser, tweet);

        if (hasGiveLike) {
            return "error";
        }

        return "redirect:/";
    }

    /**
     * When the retqeet buttons is pressed, it will check if the user is giving or removing the retweet
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping("/tweet/retweet/{id}")
    public String toggleRetweet(@PathVariable("id") Long id,
                              HttpServletRequest request) throws IOException{
        Tweet tweet = this.tweetService.findById(id).get();
        User currentUser = this.informationManager.getCurrentUser(request);
        boolean hasGiveRetweet = this.tweetService.toggleRetweet(currentUser, tweet);

        if (hasGiveRetweet) {
            return "error";
        }

        return "redirect:/";
    }

    /**
     * When the bookmark buttons is pressed, it will check if the user is adding or removing the bookmark
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping("/tweet/bookmark/{id}")
    public String toggleBookmark(@PathVariable("id") Long id, HttpServletRequest request){
        Tweet tweet = this.tweetService.findById(id).get();
        User currentUser = this.informationManager.getCurrentUser(request);
        this.tweetService.toggleBookmark(currentUser, tweet);
        return "redirect:/bookmarks";
    }

    /**
     * Deletes a tweet from the database
     * @param id
     */
    @GetMapping("/tweet/delete/{id}")
    public void deleteTweet(@PathVariable("id") Long id) {
        Tweet tweet = this.tweetService.findById(id).get();
        this.tweetService.deleteTweet(tweet);
    }

}
