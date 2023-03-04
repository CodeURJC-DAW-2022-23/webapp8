package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.TweetInformation;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.security.Principal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "{id}")
    public Tweet getOneTweet(@PathVariable("id") Long id) {
        return tweetService.findById(id).orElse(null);
    }

    @GetMapping("/{id}/tweet")
    public List<Tweet> get10Tweet(@PathVariable("id") Long id) {
        List<Tweet> t = tweetService.find10(1L);
        return new ArrayList<>();
    }

    @GetMapping("/{id}/casa")
    public String getRecent(@PathVariable("id") Long id) {
        List<Tweet> t = tweetService.find10RecentForUser(id,0,10);
        return "";
    }

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
        List<Tweet> newTweets =
                this.tweetService.find10RecentForUser(currentUser.getId(), from, size);
        List<TweetInformation> tweets = this.informationManager.calculateDataOfTweet(model, newTweets);
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
                                             @Param("from") int from,
                                             @Param("size") int size) {
        User currentUser = this.informationManager.getCurrentUser(request);
        List<Tweet> newTweets =
                this.profileService.getBookmarks(currentUser.getId(), from, size);
        List<TweetInformation> tweets =
                this.informationManager.calculateDataOfTweet(model, newTweets);
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
    /*
    @GetMapping("/profile/tweets")
    public String loadMoreTweetsForProfile(Model model,
                                           @Param("from") int from,
                                           @Param("size") int size) {
        List<Trend> newTweets = this.tweetService.getSomeUserTweets(from, size);
        model.addAttribute("tweets", newTweets);

        return "tweet";
    }
     */

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
        this.tweetService.createTweet(tweet_info, files, userId);
        saveHashtag(tweet_info);

        return "redirect:/home";
    }

    @PostMapping("/tweets/reply-tweet")
    public String postTweet(@RequestParam String tweet_info,
                            @RequestParam MultipartFile [] tweet_files,
                            @RequestParam("user_reply") Long id,
                            HttpServletRequest request) throws IOException {
        Blob [] files = this.manageImages(tweet_files);
        User currentUser = this.informationManager.getCurrentUser(request);
        Long userId = currentUser.getId();
        Tweet newTweet = this.tweetService.createTweet(tweet_info, files, userId);
        saveHashtag(tweet_info);

        Optional<User> user_owner = this.profileService.findById(id);
        User user_reply = user_owner.get();
        tweetService.addComment(tweet_info, files, user_reply, newTweet);

        return "redirect:/home";
    }

    private Blob[] manageImages(MultipartFile [] tweet_files) throws IOException {
        Blob [] files = new Blob[4];

        for (int index = 0; index < tweet_files.length; index++) {
            files[index] = BlobProxy
                    .generateProxy(tweet_files[index]
                            .getInputStream(), tweet_files[index]
                            .getSize());
        }

        return files;
    }

    private void saveHashtag(String text){
        List<Tweet> tweets = tweetService.find10(1L);
        boolean hashtagFound = false;
        String hashtag = "";
        for(int i = 0; i < text.length(); i++){
            if (text.charAt(i) =='#'){
                if (hashtagFound){
                    hashtagService.add(hashtag, tweets.get(0));
                    hashtag = "";
                }
                hashtagFound = true;
            } else if (hashtagFound){
                if (text.charAt(i) == ' ' || text.charAt(i) == '#'){
                    hashtagFound = false;
                    hashtagService.add(hashtag, tweets.get(0));
                    hashtag = "";
                } else {
                    hashtag += String.valueOf(text.charAt(i));
                    if (i == text.length()-1){
                        hashtagService.add(hashtag, tweets.get(0));
                    }
                }
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
    public String toggleBookmark(@PathVariable("id") Long id,
                                HttpServletRequest request) throws IOException{
        Tweet tweet = this.tweetService.findById(id).get();
        User currentUser = this.informationManager.getCurrentUser(request);
        this.tweetService.toggleBookmark(currentUser, tweet);

        return "error";
    }

    @GetMapping("/tweet/delete/{id}")
    public void deleteTweet(@PathVariable("id") Long id) {
        Tweet tweet = this.tweetService.findById(id).get();
        this.tweetService.deleteTweet(tweet);
    }

}
