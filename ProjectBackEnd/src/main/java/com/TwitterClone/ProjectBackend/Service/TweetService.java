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

    public List<Tweet> find10(Long id, int from, int size){
        return tweetRepository.findByUser(id, from, size);
    }

    public List<Tweet> find10RecentForUser (Long id, int init, int size){
        return tweetRepository.findByUserFollows(id, init, size);
    }

    public Tweet createTweet(String text, Blob [] files, Long userId){
        User user = userRepository.findById(userId).orElse(null);

        if (user == null){
            return null;
        }

        Tweet tweet = new Tweet(text, user, files);
        tweetRepository.save(tweet);
        return tweet;
    }

    public void deleteTweet(Tweet tweetToDelete){
        tweetRepository.delete(tweetToDelete);
    }

    public Optional<Tweet> findById(Long id){
        return tweetRepository.findById(id);
    }

    /**
     * Check the tweet to add or remove a like
     * @param giver
     * @param t
     * @return
     */
    public boolean toggleLike(User giver, Tweet t){
        boolean liked = false;
        if (this.isLiked(giver, t)){
            t.removeLike(giver);
        } else {
            t.addLike(giver);
            liked = true;
        }
        this.tweetRepository.save(t);
        return liked;
    }

    public boolean isLiked(User user, Tweet tweet) {
        return tweet.getLikes().contains(user);
    }

    /**
     * Check the tweet to add or remove a retweet
     * @param giver
     * @param t
     * @return
     */
    public boolean toggleRetweet(User giver, Tweet t){
        boolean retweeted = false;
        if (this.isRetweeted(giver, t)){
            t.removeRetweet(giver);
        } else {
            t.addRetweet(giver);
            retweeted = true;
        }
        this.tweetRepository.save(t);
        return retweeted;
    }

    public boolean isRetweeted(User user, Tweet tweet) {
        return tweet.getRetweets().contains(user);
    }

    /**
     * Check the tweet to add or remove a bookmark
     * @param currentUser
     * @param t
     */
    public void toggleBookmark(User currentUser, Tweet t) {
        List<Tweet> bookmarks = currentUser.getBookmarks();
        if (bookmarks.contains(t)){
            bookmarks.remove(t);
        }else{
            bookmarks.add(t);
        }
        currentUser.setBookmarks(bookmarks);
        this.userRepository.save(currentUser);
    }
    public boolean isBookmarked(User user, Tweet tweet) {
        return user.getBookmarks().contains(tweet);
    }

    /**
     * Add a comment to a tweet
     * @param text
     * @param files
     * @param user
     * @param tweet
     */
    public void addComment(String text, Blob[] files, User user, Tweet tweet){
        Tweet t = this.findById(tweet.getId()).orElse(null);

        if (t == null){
            return;
        }

        t.addComment(tweet);
        this.tweetRepository.save(t);
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