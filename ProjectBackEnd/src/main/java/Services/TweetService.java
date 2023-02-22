package com.TwitterClone.ProjectBackEnd.Services;

import com.TwitterClone.ProjectBackEnd.Model.Tweet;
import com.TwitterClone.ProjectBackEnd.Repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Tweet> findAll(){
        return repository.findAll();
    }

    public void createTweet(String text){
        Tweet tweet = new Tweet(text, null);
        repository.save(tweet);
    }

    public Optional<Tweet> findById(UUID id){
        return repository.findById(id);
    }

}