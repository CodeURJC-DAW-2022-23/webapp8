package UserManagement;

import Model.Tweet;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * It is a type of user who has created an account and is registered in the DB.
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "users")


public class User implements UserDetails {
    private final @Id @GeneratedValue UUID id;
    private  String nickname;
    private  String biography;
    private  String mail;
    private  String password;
    private  Blob profilePicture;
    private  Blob profileBanner;
    private String username;
    @Enumerated(EnumType.STRING)
    private UserRoles role;
    private boolean loggedIn;
    private ArrayList<UUID> followers;
    private ArrayList<UUID> followed;
    private ArrayList<Tweet> tweetsTShow;

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
        this.id = UUID.randomUUID();
        this.joinDate = java.time.LocalDate.now();
        this.loggedIn = false;
    }

    public User() {
        this.id = UUID.randomUUID();
        this.joinDate = java.time.LocalDate.now();
    }

    /*
   This constructor is for sample data
    */
    public User(String username, String nickname, String biography, String mail, String password, String [] files, LocalDate time, String role) throws IOException {
        this.id = UUID.randomUUID();
        this.username = username;
        this.nickname = nickname;
        this.biography = biography;
        this.joinDate = time;
        this.role = UserRoles.valueOf("Registered");
        this.mail = mail;
        this.password = password;
        this.setImages(files);
    }

    private void setImages(String[] files) {
    }

    /**
     * It sets the authority level according to the user role
     * @return
     */
    @Override
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
    }
}
