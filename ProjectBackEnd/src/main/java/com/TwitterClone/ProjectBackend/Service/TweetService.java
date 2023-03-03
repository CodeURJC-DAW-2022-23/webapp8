package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Trend;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.TweetRepository;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.sql.Blob;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * This class is on charge of implementing all the necessary logic for the Tweets.
 */
@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired UserRepository userRepository;


    public List<Tweet> findAll(){
        return tweetRepository.findAll();
    }

    public List<Tweet> find10(){
        User user = userRepository.findById(1L).orElse(null);
        return tweetRepository.findByUser(user.getId());
    }

    public List<Tweet> find10RecentForUser (Long id){
        return tweetRepository.findByUserFollows(id);
    }

    public void createTweet(String text, Blob [] files, Long userId){
        User user = userRepository.findById(userId).orElse(null);
        if (user != null){
            Tweet tweet = new Tweet(text, user, files);
            tweetRepository.save(tweet);
        }
    }

    public void deleteTweet(Tweet tweetToDelete){
        tweetRepository.delete(tweetToDelete);
    }

    public Optional<Tweet> findById(Long id){
        return tweetRepository.findById(id);
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
            Tweet comment = new Tweet(text, user, files);
            t.addComment(comment);
        }
    }



    /**
     * Ask the database for more tweets for the user
     * @param init
     * @param size
     * @return
     */
    /*
    public List<Trend> getSomeTweetsForUser(int init, int size) {
        Page<Tuple> tweets = this.tweetRepository.(PageRequest.of(init,size));
        List<Tuple> tweetList = tweets.stream().toList();
        return this.converterToTweets(tweetList);
    }
    */

    /**
     * Ask the database for more bookmark tweets
     * @param init
     * @param size
     * @return
     */
    /*
    public List<Trend> getSomeBookmarkTweets(int init, int size) {
        Page<Tuple> tweets = this.tweetRepository.(PageRequest.of(init,size));
        List<Tuple> tweetList = tweets.stream().toList();
        return this.converterToTweets(tweetList);
    }
     */


    /**
     * Ask the database for more tweets from the user
     * @param init
     * @param size
     * @return
     */
    /*
    public List<Trend> getSomeUserTweets(int init, int size) {
        Page<Tuple> tweets = this.tweetRepository.(PageRequest.of(init,size));
        List<Tuple> tweetList = tweets.stream().toList();
        return this.converterToTweets(tweetList);
    }
    */

    /**
     * Transform the list of Tuples to a list of Tweets
     * @param tweetList
     * @return
     */
    private List<Trend> converterToTweets(List<Tuple> tweetList) {
        List<Trend> list = new LinkedList<>();

        for (int i = 0; i < tweetList.size(); i++){
            String numTweets = tweetList.get(i).get("numtweets").toString();
            String hashtag = tweetList.get(i).get("hashtag").toString();
            list.add(new Trend(hashtag,Integer.parseInt(numTweets)));
        }

        return list;
    }

    /**
     * Get the number of likes of a tweet
     * @param id
     * @return
     */
    public Long getLikesOfTweet(Long id) {
        Long number = this.tweetRepository.countLikes(id);

        if (number == null) {
            return 0L;
        }

        return number;
    }

    /**
     * Get the number of comments of a tweet
     * @param id
     * @return
     */
    public Long getCommentsOfTweet(Long id) {
        Long number = this.tweetRepository.countComments(id);

        if (number == null) {
            return 0L;
        }

        return number;
    }

    /**
     * Get the number of retweets of a tweet
     * @param id
     * @return
     */
    public Long getRetweetsOfTweet(Long id) {
        Long number = this.tweetRepository.countRetweets(id);

        if (number == null) {
            return 0L;
        }

        return number;
    }
}