package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.TweetRepository;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class is on charge of implementing all the necessary logic for the Tweets.
 */
@Service
public class TweetService {

    @Autowired
    private TweetRepository repository;

    @Autowired UserRepository userRepository;

    public List<Tweet> findAll(){
        return repository.findAll();
    }

    public void createTweet(String text, Blob [] files, Tweet citation, Long userId){
        User user = userRepository.findById(userId).orElse(null);
        if (user != null){
            Tweet tweet = new Tweet(text, user, files, citation);
            repository.save(tweet);
            List<Tweet> tweets = repository.findAll();
            user.addTweet(tweets.get(tweets.size()-1));
            userRepository.save(user);
        }
    }

    public void deleteTweet(Tweet tweetToDelete){
        repository.delete(tweetToDelete);
    }

    public Optional<Tweet> findById(Long id){
        return repository.findById(id);
    }

    /*
    This function will add or remove the like
     */
    public void toggleLike(User giver, Tweet tweet){
        Tweet t = this.findById(tweet.getId()).orElse(null);
        if (t != null){
            if (t.getLikes().contains(giver)){
                t.removeLike(giver);
            }
            else{
                t.addLike(giver);
            }
        }
    }

    /*
    This function will add or remove the retweet
     */
    public void toggleRetweet(User giver, Tweet tweet){
        Tweet t = this.findById(tweet.getId()).orElse(null);
        if (t != null){
            if (t.getRetweets().contains(giver)){
                t.removeRetweet(giver);
            }
            else{
                t.addRetweet(giver);
            }
        }
    }

    /*
    This function will add a Commment
     */
    public void addComment(String text, Blob[] files, User user, Tweet tweet){
        Tweet t = this.findById(tweet.getId()).orElse(null);
        if (t != null){
            Tweet comment = new Tweet(text, user, files, null);
            t.addComment(comment);
        }
    }
}