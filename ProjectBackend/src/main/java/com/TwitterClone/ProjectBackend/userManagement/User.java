package com.TwitterClone.ProjectBackend.userManagement;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.userManagement.UserRoles;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * It is a type of user who has created an account and is registered in the DB.
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "users")


public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String username;
    @Column(unique=true)
    private  String mail;
    private  String nickname;
    private  String biography;
    private  String password;
    @Lob
    private  Blob profilePicture;
    @Lob
    private  Blob profileBanner;
    @Enumerated(EnumType.STRING)
    private UserRoles role;
    @OneToMany
    private List<User> followers;
    @OneToMany
    private List<User> followed;
    @OneToMany
    private List<Tweet> tweetsWritten;
    private final LocalDate joinDate;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    private boolean loggedIn;
    private boolean enabled = true;

    /**
     * Main constructor.
     */

    public User(@NotNull @NotBlank String username,
                @NotNull @NotBlank String password,
                @NotNull @NotBlank String email,
                String role) {
        super();
        this.username = username;
        this.password = password;
        this.mail = email;
        this.id = new Random().nextLong();
        if (role.isBlank()) this.role = UserRoles.valueOf("USER");
        else this.role = UserRoles.valueOf(role);
        this.joinDate = java.time.LocalDate.now();
        this.followers = new LinkedList<>();
        this.followed = new LinkedList<>();
        this.tweetsWritten = new LinkedList<>();
        this.loggedIn = false;
    }

    public User() {
        this.joinDate = java.time.LocalDate.now();
    }

    /*
   This constructor is for sample data
    */
    public User(String username, String nickname, String biography, String mail, String password, String [] files, LocalDate time, String role) throws IOException {
        this.id = new Random().nextLong();
        this.username = username;
        this.nickname = nickname;
        this.biography = biography;
        this.joinDate = time;
        this.role = UserRoles.valueOf(role);
        this.mail = mail;
        this.password = password;
        this.followers = new LinkedList<>();
        this.followed = new LinkedList<>();
        this.tweetsWritten = new LinkedList<>();
        this.setImages(files);
    }

    private void setImages(String[] files) {
    }


    public void addTweet(Tweet tweet){

        this.tweetsWritten.add(tweet);
    }
}
