package Services;

import Model.Bookmark;
import Model.Tweet;
import Model.User;
import Repository.TweetDataAccessService;

import java.util.UUID;

/**
 * This class is on charge of implementing all the necessary logic for the Tweets.
 */
public class TweetService {

    private Tweet tweet;
    private TweetDataAccessService tweetDao;

    /**
     * This method publish a new tweet on the followers feed and save it to the db.
     * @return
     */
    public Tweet publishNewTweet(){
        return new Tweet();
    }

    /**
     * This method takes the id of a tweet and safely delete it from the DB
     * @param tweetId
     */
    public void deleteTweet(UUID tweetId){

    }

    /**
     * This method add a like to the likes list if it was not there before or delete if it was already on the likes list
     * of the tweet from which the petition came
     * @param giver
     */
    public void like(User giver){

    }

    /**
     * This method add a retweet to the likes list if it was not there before or delete if it was already on the retweet
     * list of the tweet from which the petition came
     * @param giver
     */
    public void retweet(User giver){

    }

    /**
     * This method create and adds a new comment to the tweet from which the petition came
     * @param author
     */
    public void addComment(User author){

    }

    /**
     * This method add a tweet to the booksmarks of a user
     * @return
     */
    public Bookmark addBookmark(){
        return new Bookmark();
    }

    /**
     * This method deletes a tweet from the bookmarks of a user
     * @param bookmark
     */
    public void deleteBookmark(Bookmark bookmark){

    }

}
