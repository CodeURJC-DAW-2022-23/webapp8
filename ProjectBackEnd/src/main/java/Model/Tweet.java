package Model;

import UserManagement.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @OneToOne
    private User user;
    private final LocalDateTime publishDate;
    @OneToMany
    private List<User> likes;
    @OneToMany
    private List<User> retweets;
    @OneToMany
    private List<Tweet> comments;
    @OneToOne
    private Tweet citation;
    private String text;
    //@Lob
    //private Blob[] media;
    //private Set<String> hashtag; // Later

    public Tweet(){
        this.id = UUID.randomUUID();
        this.publishDate = LocalDateTime.now();
    }
    public Tweet(String text, User user) {
        this.id = UUID.randomUUID();
        this.publishDate = LocalDateTime.now();
        this.likes = new LinkedList<User>();
        this.retweets =  new LinkedList<User>();
        this.comments = new LinkedList<Tweet>();
        this.text = text;
        this.user = user;
    }

    /*
    For example data
     */
    public Tweet(String text, User user, LocalDateTime time, Tweet citation) {
        this.id = UUID.randomUUID();
        this.publishDate = time;
        this.likes = new LinkedList<User>();
        this.retweets =  new LinkedList<User>();
        this.comments = new LinkedList<Tweet>();
        this.text = text;
        this.user = user;
        this.citation = citation;
    }


}