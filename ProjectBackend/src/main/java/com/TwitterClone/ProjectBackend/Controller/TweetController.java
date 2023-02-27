package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("tweets/")
@Controller
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @GetMapping
    public List<Tweet> getAllTweets() {
        return tweetService.findAll();
    }

    @GetMapping(path = "{id}")
    public Tweet getOneTweet(@PathVariable("id") Long id) {
        return tweetService.findById(id).orElse(null);
    }

    @GetMapping("{id}/tweet/")
    public List<Tweet> get10Tweet(@PathVariable("id") Long id) {
        List<Tweet> t = tweetService.find10();
        return new ArrayList<>();
    }

    @GetMapping("{id}/casa")
    public List<Tweet> getRecent(@PathVariable("id") Long id) {
        List<Tweet> t = tweetService.find10RecentForUser(id);
        return new ArrayList<>();
    }
    @PostMapping("post/")
    public void postTweet(@RequestParam("text") String text,@RequestParam("files") MultipartFile [] images) throws IOException {
        Blob [] files = new Blob[4];

        if (images.length > 0){
            files[0] = BlobProxy.generateProxy(images[0].getInputStream(),images[0].getSize());
        }

        if (images.length > 1){
            files[1] = BlobProxy.generateProxy(images[1].getInputStream(),images[1].getSize());
        }

        if (images.length > 2){
            files[2] = BlobProxy.generateProxy(images[2].getInputStream(),images[2].getSize());
        }

        if (images.length > 3){
            files[3] = BlobProxy.generateProxy(images[3].getInputStream(),images[3].getSize());
        }

        Long userId = 1L;
        tweetService.createTweet(text, files,null, userId);
    }

    @PostMapping("like/")
    public void toggleLike(@RequestBody Tweet tweet){
        User user = new User();//Needs to be redone
        tweetService.toggleLike(user, tweet);
    }

    @PostMapping("retweet/")
    public void toggleRetweet(@RequestBody Tweet tweet){
        User user = new User();//Needs to be redone
        tweetService.toggleRetweet(user, tweet);
    }

    @PostMapping("delete/")
    public void deleteTweet(@RequestBody Tweet tweet){
        tweetService.deleteTweet(tweet);
    }

    @PostMapping("comment/")
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
