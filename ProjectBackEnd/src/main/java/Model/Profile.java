package Model;

/**
 * This class manages showing all the profile's information owned by an user and showing its tweets
 */
public class Profile {
    private String profilePic;
    private String profileBanner;
    private String biography;
    private int numFollowers;
    private int numFollowed;
    private RegisteredUser userRelated;

    /**
     * Prepares the object to be used by templates
     * @param idUserRelated the user whose information and tweets will be shown
     */
    public void showInformation(String idUserRelated){

    }

    /**
     * Obtains tweets from TweetController
     */
    private void askTweets(){

    }

    private void askFollowers(){

    }
}
