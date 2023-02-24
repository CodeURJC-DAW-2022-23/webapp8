package com.TwitterClone.ProjectBackend.userManagement;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.userManagement.UserRoles;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;*/
import org.hibernate.annotations.Generated;
import org.hibernate.validator.constraints.UniqueElements;

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


public class User /*implements UserDetails*/ {
    private final @Id @GeneratedValue Long id;
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
    private boolean loggedIn;
    @OneToMany
    private List<User> followers;
    @OneToMany
    private List<User> followed;
    @OneToMany
    private List<Tweet> tweetsWritten;
    private final LocalDate joinDate;
    private Boolean locked;
    private Boolean enabled;

    /**
     * Main constructor. The UUID is randomly given and the join date is the date of registration.
     */

    public User(@NotNull @NotBlank String username,
                @NotNull @NotBlank String password,
                @NotNull @NotBlank String email) {
        this.username = username;
        this.password = password;
        this.mail = email;
        this.id = new Random().nextLong();
        this.role = UserRoles.valueOf("USER");
        this.joinDate = java.time.LocalDate.now();
        this.followers = new LinkedList<>();
        this.followed = new LinkedList<>();
        this.tweetsWritten = new LinkedList<>();
        this.loggedIn = false;
    }

    public User() {
        this.id = new Random().nextLong();
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

    /**
     * It sets the authority level according to the user role
     * @return
     */
   /* @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }*/
}
