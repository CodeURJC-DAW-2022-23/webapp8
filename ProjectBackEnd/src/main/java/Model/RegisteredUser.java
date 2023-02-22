package Model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * It is a type of user who has created an account and is registered in the DB.
 */
@Getter
@Setter
@Entity
@Table(name = "users")

public class RegisteredUser {
    private final @Id @GeneratedValue UUID id;
    private String username;
    private String paswword;
    private String email;
    private boolean loggedIn;
    private ArrayList<UUID> followers;
    private ArrayList<UUID> followed;
    private ArrayList<Tweet> tweetsTShow;
    private String bio;
    private File profilePicture;
    private final LocalDate joinDate;

    /**
     * Main constructor. The UUID is randomly given and the join date is the date of registration.
     */

    public RegisteredUser(@NotNull @NotBlank String username,
                          @NotNull @NotBlank String password,
                          @NotNull @NotBlank String email) {
        this.username = username;
        this.paswword = password;
        this.email = email;
        this.id = UUID.randomUUID();
        this.joinDate = java.time.LocalDate.now();
        this.loggedIn = false;
    }

    public RegisteredUser() {
        this.id = UUID.randomUUID();
        this.joinDate = java.time.LocalDate.now();
    }

}
