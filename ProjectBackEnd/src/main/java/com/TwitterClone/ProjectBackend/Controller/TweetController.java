package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/tweets")
@Controller
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @Autowired
    private HashtagService hashtagService;

    @Autowired
    private ProfileService profileService;

    @GetMapping("/")
    public List<Tweet> getAllTweets() {
        return tweetService.findAll();
    }

    @GetMapping(path = "{id}")
    public Tweet getOneTweet(@PathVariable("id") Long id) {
        return tweetService.findById(id).orElse(null);
    }

    @GetMapping("/{id}/tweet/")
    public List<Tweet> get10Tweet(@PathVariable("id") Long id) {
        List<Tweet> t = tweetService.find10();
        return new ArrayList<>();
    }

    @GetMapping("/{id}/casa")
    public String getRecent(@PathVariable("id") Long id) {
        List<Tweet> t = tweetService.find10RecentForUser(id);
        return "";
    }
    @PostMapping("/new-tweet")
    public String postTweet(@RequestParam("tweet-info") String text,
                            @RequestParam("tweet-files") MultipartFile [] images,
                            HttpServletRequest request) throws IOException {
        Blob [] files = new Blob[4];

        for (int index = 0; index < images.length; index++) {
            files[index] = BlobProxy
                    .generateProxy(images[index]
                            .getInputStream(), images[index]
                            .getSize());
        }

        Principal principal = request.getUserPrincipal();
        Optional<User> currentSession = this.profileService.findByUsername(principal.getName());
        User currentUser = currentSession.get();

        Long userId = currentUser.getId();
        tweetService.createTweet(text, files,null, userId);

        saveHashtag(text);

        return "redirect:/home";
    }

    private void saveHashtag(String text){
        List<Tweet> tweets = tweetService.find10();
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

    @PostMapping("/like")
    public void toggleLike(@RequestBody Tweet tweet) throws IOException {
        User user = new User();//Needs to be redone
        tweetService.toggleLike(user, tweet);
    }

    @PostMapping("/retweet")
    public void toggleRetweet(@RequestBody Tweet tweet){
        User user = new User();//Needs to be redone
        tweetService.toggleRetweet(user, tweet);
    }

    @PostMapping("/delete")
    public void deleteTweet(@RequestBody Tweet tweet){
        tweetService.deleteTweet(tweet);
    }

    @PostMapping("/comment")
    public void postComment(@RequestBody String text, MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4,@RequestBody Tweet tweetCommented) throws IOException {
        Blob [] files = new Blob[4];

        if (image1 != null){
            files[0] = BlobProxy.generateProxy(image1.getInputStream(),image1.getSize());
        }

        if (image2 != null){
            files[1] = BlobProxy.generateProxy(image2.getInputStream(),image2.getSize());
        }

        if (image3 != null){
            files[2] = BlobProxy.generateProxy(image3.getInputStream(),image3.getSize());
        }

        if (image4 != null){
            files[3] = BlobProxy.generateProxy(image4.getInputStream(),image4.getSize());
        }

        User user = new User();//Needs to be redo
        tweetService.addComment(text, files, user, tweetCommented);
    }
}