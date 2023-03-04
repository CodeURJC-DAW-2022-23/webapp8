package com.TwitterClone.ProjectBackend.Model;

import com.TwitterClone.ProjectBackend.userManagement.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * This is Notification Entity. When a user interacts with either your profile or your tweets, a notification will be
 * generated in order to report you about your influence in other users.
 */
@Entity
@Getter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @ManyToOne
    private Tweet tweetTrigger;
    @ManyToOne
    private User userToNotify;
    @ManyToOne
    private User userWhoNotifies;
    private LocalDateTime date;
    private String type;

    /**
     * Deafult constructor
     */
    public Notification() {
        id = new Random().nextLong();
    }

    /**
     * Constructor
     * @param tweet
     * @param userToNotify
     * @param userWhoNotifies
     */
    public Notification(Tweet tweet, User userToNotify, User userWhoNotifies, String type) {
        id = new Random().nextLong();
        this.userToNotify = userToNotify;
        this.userWhoNotifies = userWhoNotifies;
        this.tweetTrigger = tweet;
        this.date = LocalDateTime.now();
        this.type = type;
    }

    /**
     * This constructor is for sample data
     * @param tweet
     * @param userToNotify
     * @param userWhoNotifies
     * @param date
     */
    public Notification(Tweet tweet, User userToNotify, User userWhoNotifies, LocalDateTime date, String type) {
        id = new Random().nextLong();
        this.userToNotify = userToNotify;
        this.userWhoNotifies = userWhoNotifies;
        this.tweetTrigger = tweet;
        this.date = date;
        this.type = type;
    }
}
