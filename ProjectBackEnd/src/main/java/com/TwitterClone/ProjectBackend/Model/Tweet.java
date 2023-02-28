package com.TwitterClone.ProjectBackend.Model;

import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
public class Tweet {
    public interface Basic {}
    @Id
    @GeneratedValue
    @JsonView(Basic.class)
    private final Long id;
    @ManyToOne
    @JsonView(Basic.class)
    private User user;
    @JsonView(Basic.class)
    private final LocalDateTime publishDate;
    @ManyToMany (fetch = FetchType.EAGER)
    private List<User> likes = new ArrayList<>();
    @ManyToMany (fetch = FetchType.EAGER)
    private List<User> retweets = new ArrayList<>();
    @OneToMany (fetch = FetchType.EAGER)
    private List<Tweet> comments = new ArrayList<>();
    @JsonView(Basic.class)
    private String text;
    @Lob
    private Blob media1;
    @Lob
    private Blob media2;
    @Lob
    private Blob media3;
    @Lob
    private Blob media4;

    public Tweet(){
        this.id = new Random().nextLong();
        this.publishDate = LocalDateTime.now();
    }
    public Tweet(String text, User user, Blob [] files, Tweet citation) {
        this.id = new Random().nextLong();
        this.publishDate = LocalDateTime.now();
        this.likes = new LinkedList<>();
        this.retweets =  new LinkedList<>();
        this.comments = new LinkedList<>();
        this.text = text;
        this.user = user;
        this.media1 = files[0];
        this.media2 = files[1];
        this.media3 = files[2];
        this.media4 = files[3];
        //this.citation = citation;
    }

    /*
    For example data
     */
    public Tweet(String text, User user, LocalDateTime time, Tweet citation, Blob [] files) {
        this.id = new Random().nextLong();
        this.publishDate = time;
        this.likes = new LinkedList<>();
        this.retweets =  new LinkedList<>();
        this.comments = new LinkedList<>();
        this.text = text;
        this.user = user;
        //this.citation = citation;
        this.media1 = files[0];
        this.media2 = files[1];
        this.media3 = files[2];
        this.media4 = files[3];
    }

    public void addComment(Tweet comment){
        this.comments.add(comment);
    }

    public void addLike(User giver){
        this.likes.add(giver);
    }

    public void addRetweet(User giver){
        this.retweets.add(giver);
    }

    public void removeLike(User giver){
        this.likes.remove(giver);
    }

    public void removeRetweet(User giver){
        this.retweets.remove(giver);
    }

}