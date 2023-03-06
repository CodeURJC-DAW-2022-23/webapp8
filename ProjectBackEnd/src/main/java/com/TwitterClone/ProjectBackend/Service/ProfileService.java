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
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void createProfile(String username, String password, String mail) throws IOException {
        User user = new User(username, password, mail, "USER");
        userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Tweet> getBookmarks(Long id, int offset, int size) {
        return tweetRepository.findBookmarksByUserId(id, offset, size);
    }

    public List<User> getVerified(int init, int size) {
        return userRepository.findVerified(init, size);
    }

    public List<User> getFollowers(Long id, int from, int size) {
        return userRepository.findFollowers(id, from, size);
    }

    public List<User> getBanned(int init, int size){
        return userRepository.findBanned(init,size);
    }

    public List<User> getFollowed(Long id, int from, int size) {
        return userRepository.findFollowed(id, from, size);
    }

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

    private Blob prepareImageFile(MultipartFile file) throws IOException {
        return BlobProxy
                .generateProxy(file
                        .getInputStream(), file
                        .getSize());
    }

    public List<User> getToVerified(int init, int size) {
        return this.userRepository.findNotVerifiedNotBanned(init,size);
    }

    public List<Tuple> getStatics(){
       return this.userRepository.countByLast5JoinDate();
    }

    public void updateUserBan(User user) {
        this.userRepository.save(user);
    }

    public int countBookmarks(Long id) {
        return this.tweetRepository.countBookmarks(id);
    }

    public int countTweetsForUser(Long id) {
        return this.tweetRepository.countTweetsForUser(id);
    }

    public long countFollowed(Long id) {
        return this.userRepository.countFollowed(id);
    }

    public long countFollowers(Long id) {
        return this.userRepository.countFollowers(id);
    }

    public int countTweetsOfUser(Long id) {
        return  this.tweetRepository.countUserTweets(id);
    }

    public boolean isFollowedBy(User profileUser, User currentUser) {
        return profileUser.getFollowers().contains(currentUser);
    }

    public void toggleFollow(Long idProfileUser, Long idCurrentUser) {
        User profileUser = this.findById(idProfileUser).get();
        User currentUser = this.findById(idCurrentUser).get();
        if (!this.isFollowedBy(profileUser, currentUser)){
            profileUser.addFollower(currentUser);
            currentUser.addFollowed(profileUser);
        } else {
            profileUser.removeFollower(currentUser);
            currentUser.removeFollowed(profileUser);
        }
        this.userRepository.save(profileUser);
        this.userRepository.save(currentUser);
    }
}
