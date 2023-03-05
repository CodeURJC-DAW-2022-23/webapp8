package com.TwitterClone.ProjectBackend.Model;

import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This is Tweet Entity. A Tweet is the core of the application.
 * Writing a Tweet is the way to connect with people around the world.
 * It is composed by a text of a maximum of 240 characters where Users can share with the world how they feel.
 * This text could go along with a maximum of 4 pictures that the user can upload.
 * Those pictures are stored in our DataBase as Blobs in order to make DB more scalable.
 */
@Getter
@Setter
@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private final LocalDateTime publishDate;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> likes = new ArrayList<>();
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> retweets = new ArrayList<>();
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tweet> comments = new ArrayList<>();
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
        this.publishDate = LocalDateTime.now();
    }

    public Tweet(String text, User user, Blob [] files) {
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
    }

    /*
    For example data
     */
    public Tweet(String text, User user, LocalDateTime time, Blob [] files) {
        this.publishDate = time;
        this.likes = new LinkedList<>();
        this.retweets =  new LinkedList<>();
        this.comments = new LinkedList<>();
        this.text = text;
        this.user = user;
        if (files[0] != null) {
            this.media1 = files[0];
        }
        if (files[1] != null) {
            this.media2 = files[1];
        }
        if (files[2] != null) {
            this.media3 = files[2];
        }
        if (files[3] != null) {
            this.media4 = files[3];
        }
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
