package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;
import java.util.UUID;

@RequestMapping("tweets/")
@RestController
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @GetMapping
    public List<Tweet> getAllTweets() {
        return tweetService.findAll();
    }

    @GetMapping(path = "{id}")
    public Tweet getOneTweet(@PathVariable("id") UUID id) {
        return tweetService.findById(id).orElse(null);
    }

    @PostMapping("post/")
    public void postTweet(@RequestBody String text, MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4, Tweet citation) throws IOException {
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
        tweetService.createTweet(text, files, citation, user);
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
