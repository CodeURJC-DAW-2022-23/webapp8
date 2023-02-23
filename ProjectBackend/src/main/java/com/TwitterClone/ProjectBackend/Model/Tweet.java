package com.TwitterClone.ProjectBackend.Model;

import com.TwitterClone.ProjectBackend.userManagement.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Tweet {
    @Id
    @Column(name = "id", length = 16, unique = true, nullable = false)
    private final UUID id;
    @ManyToOne
    private User user;
    private final LocalDateTime publishDate;
    @OneToMany
    private List<User> likes;
    @OneToMany
    private List<User> retweets;
    @OneToMany
    private List<Tweet> comments;
    @ManyToOne
    private Tweet citation;
    private String text;
    //@Lob
    //@OneToMany
    //private List<Blob> media;

    //private Set<String> hashtag; // Later

    public Tweet(){
        this.id = UUID.randomUUID();
        this.publishDate = LocalDateTime.now();
    }
    public Tweet(String text, User user, Blob [] files, Tweet citation) {
        this.id = UUID.randomUUID();
        this.publishDate = LocalDateTime.now();
        this.likes = new LinkedList<>();
        this.retweets =  new LinkedList<>();
        this.comments = new LinkedList<>();
        this.text = text;
        this.user = user;
        //this.media = new LinkedList<>(List.of(files));
        this.citation = citation;
    }

    /*
    For example data
     */
    public Tweet(String text, User user, LocalDateTime time, Tweet citation, Blob [] files) {
        this.id = UUID.randomUUID();
        this.publishDate = time;
        this.likes = new LinkedList<>();
        this.retweets =  new LinkedList<>();
        this.comments = new LinkedList<>();
        this.text = text;
        this.user = user;
        this.citation = citation;
        //this.media = new LinkedList<>(List.of(files));
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
