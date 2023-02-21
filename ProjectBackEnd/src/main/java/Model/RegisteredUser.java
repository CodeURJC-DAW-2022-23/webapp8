package Model;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * It is a type of user who has created an account and is registered in the DB.
 */

public class RegisteredUser {
    private final UUID id;
    private String username;
    private ArrayList<UUID> followers;
    private ArrayList<UUID> followed;
    private ArrayList<Tweet> tweetsTShow;
    private String bio;
    private File profilePicture;
    private final LocalDate joinDate;

    /**
     * Main constructor. The UUID is randomly given and the join date is the date of registration.
     */
    public RegisteredUser() {
        this.id = UUID.randomUUID();
        this.joinDate = java.time.LocalDate.now();
    }
}
