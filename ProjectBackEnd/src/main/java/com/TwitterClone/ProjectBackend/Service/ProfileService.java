package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.TweetRepository;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

/**
 * Connect the profile controller with the user repository
 */
@Service
public class ProfileService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;

    /**
     * Obtain a user using an id
     * @param id
     * @return
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Obtain a user using a username
     * @param username
     * @return
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Obtain some bookmarks from current user
     * @param id
     * @param offset
     * @param size
     * @return
     */
    public List<Tweet> getBookmarks(Long id, int offset, int size) {
        return tweetRepository.findBookmarksByUserId(id, offset, size);
    }

    /**
     * Obtain some verified user
     * @return
     */
    public List<User> getVerified() {
        return userRepository.findVerified();
    }

    /**
     * Obtain some followers of a user
     * @param id
     * @param from
     * @param size
     * @return
     */
    public List<User> getFollowers(Long id, int from, int size) {
        return userRepository.findFollowers(id, from, size);
    }

    /**
     * Obtain some banned users
     * @return
     */
    public List<User> getBanned(){
        return userRepository.findBanned();
    }

    /**
     * Obtain some followed user of a user
     * @param id
     * @param from
     * @param size
     * @return
     */
    public List<User> getFollowed(Long id, int from, int size) {
        return userRepository.findFollowed(id, from, size);
    }


    /**
     * Update a user profile
     * @param user
     * @param banner
     * @param profile
     * @param nickname
     * @param biography
     * @throws IOException
     */
    public void uploadProfile(User user, MultipartFile banner,
                              MultipartFile profile,
                              String nickname,
                              String biography) throws IOException {
        Long id = user.getId();
        User userToChange = this.userRepository.findById(id).get();

        userToChange.setNickname(nickname);
        userToChange.setBiography(biography);
        if (!profile.isEmpty()) {
            userToChange.setProfilePicture(this.prepareImageFile(profile));
        }
        if (!banner.isEmpty()) {
            userToChange.setProfileBanner(this.prepareImageFile(banner));
        }

        this.userRepository.save(userToChange);
    }

    /**
     * Prepare the new image to be save it in the database
     * @param file
     * @return
     * @throws IOException
     */
    private Blob prepareImageFile(MultipartFile file) throws IOException {
        return BlobProxy
                .generateProxy(file
                        .getInputStream(), file
                        .getSize());
    }

    /**
     * Obtain some user that can be verified
     * @return
     */
    public List<User> getToVerified() {
        return this.userRepository.findNotVerifiedNotBanned();
    }

    /**
     * Obtain the statistics of the website
     * @return
     */
    public List<Tuple> getStatics(){
       return this.userRepository.countByLast5JoinDate();
    }

    /**
     * Update the state of user that has been banned
     * @param user
     */
    public void updateUserBan(User user) {
        this.userRepository.save(user);
    }

    /**
     * Obtain the amount of bookmarks associated to a user
     * @param id
     * @return
     */
    public int countBookmarks(Long id) {
        return this.tweetRepository.countBookmarks(id);
    }

    /**
     * Obtain the amount of tweets for a user
     * @param id
     * @return
     */
    public int countTweetsForUser(Long id) {
        return this.tweetRepository.countTweetsForUser(id);
    }

    /**
     * Obtain the amount of followed users
     * @param id
     * @return
     */
    public long countFollowed(Long id) {
        return this.userRepository.countFollowed(id);
    }

    /**
     * Obtain the amount of followers
     * @param id
     * @return
     */
    public long countFollowers(Long id) {
        return this.userRepository.countFollowers(id);
    }

    /**
     * Obtain the amount of tweet of a user
     * @param id
     * @return
     */
    public int countTweetsOfUser(Long id) {
        return  this.tweetRepository.countUserTweets(id);
    }

    /**
     * Checks if a user is followed by another
     * @param profileUser
     * @param currentUser
     * @return
     */
    public boolean isFollowedBy(User profileUser, User currentUser) {
        return profileUser.getFollowers().contains(currentUser);
    }

    /**
     * Analyze the state of the follow by the current user
     * @param profileUser
     * @param currentUser
     * @return
     */
    public boolean toggleFollow(User profileUser, User currentUser) {
        boolean hasFollowed = false;
        if (!this.isFollowedBy(profileUser, currentUser)){
            profileUser.addFollower(currentUser);
            currentUser.addFollowed(profileUser);
            hasFollowed = true;
        } else {
            profileUser.removeFollower(currentUser);
            currentUser.removeFollowed(profileUser);
        }
        this.userRepository.save(profileUser);
        this.userRepository.save(currentUser);
        return hasFollowed;
    }
}
