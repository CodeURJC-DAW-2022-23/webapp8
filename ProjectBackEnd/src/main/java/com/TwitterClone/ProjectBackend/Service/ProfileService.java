package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.TweetRepository;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private TweetRepository tweetRepository;
    public List<User> findAll(){
        return repository.findAll();
    }

    public void createProfile(String username, String password, String mail) {
        User user = new User(username, password, mail, "USER");
        repository.save(user);
    }

    public Optional<User> findById(Long id){
        return repository.findById(id);
    }

    public Optional<User> findByUsername(String username){
        return repository.findByUsername(username);
    }

    public void updateBanner(MultipartFile banner, Long id) throws IOException {
        User u = repository.findById(id).orElse(null);
        if (u != null){
            u.setProfileBanner(BlobProxy.generateProxy(banner.getInputStream(),banner.getSize()));
            repository.save(u);
        }
    }
    public void updateProfilePicture(MultipartFile profilePicture, Long id) throws IOException {
        User u = repository.findById(id).orElse(null);
        if (u != null){
            u.setProfilePicture(BlobProxy.generateProxy(profilePicture.getInputStream(),profilePicture.getSize()));
            repository.save(u);
        }
    }

    public void updateBiography(String biography, User user) {
        User u = repository.findById(user.getId()).orElse(null);
        if (u != null){
            u.setBiography(biography);
            repository.save(u);
        }
    }
    public List<Tweet> getBookmarks (Long id,int offset,int size){
        return tweetRepository.findBookmarksByUserId(id,offset,size);
    }

    public List<User> getVerified(int init, int size) {
        return repository.findVerified(init, size);
    }

    public List<User> getFollowers(Long id) {
        return repository.findFollowers(id);
    }

    public List<User> getBanned(int init, int size){
        return repository.findBanned(init,size);
    }

    public List<User> getFollowed(Long id) {
        return repository.findFollowed(id);
    }

    public List<User> getToVerified(int init, int size) {
        return this.repository.findNotVerifiedNotBanned(init,size);
    }

    public List<Tuple> getStatics(){
       return this.repository.countByLast5JoinDate();
    }

    public void updateType(User user) {
        this.repository.save(user);
    }

    public int countBookmarks(Long id) {
        return this.tweetRepository.countBookmarks(id);
    }

    public int countTweetsForUser(Long id) {
        return this.tweetRepository.countTweetsForUser(id);
    }
}
