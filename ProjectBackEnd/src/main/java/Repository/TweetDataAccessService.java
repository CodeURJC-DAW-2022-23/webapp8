package Repository;

import Model.Tweet;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class is the access point to the tweets data base. It is on charge of safely retrieving the necessary data from
 * it.
 */
public class TweetDataAccessService {

    private ArrayList<Tweet> userPersonalTweets;

    public Tweet retrieveTweet(UUID tweetId){
        return new Tweet();
    }

    public ArrayList<Tweet> retrieveAllUserTweets(UUID userId){
        return new ArrayList<Tweet>();
    }

    public void deleteTweet(UUID tweetId){

    }

}
