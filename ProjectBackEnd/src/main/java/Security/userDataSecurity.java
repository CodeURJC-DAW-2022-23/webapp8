package Security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is in charge of controlling the security part of the login procedure by using Spring security modules
 */

public class userDataSecurity {

    /**
     * This method receives the data from the logIn form and return true if the username and password are correct.
     * @param username
     * @param password
     * @return
     */
    public boolean checkLogInData(String username, String password){
        return (checkPassword(password) && checkUsername(username)); // Return the form after processing it
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method take the password given by the user and check it against the DB to see if it is correct
     * @param password
     * @return
     */
    private boolean checkPassword (String password){

        return true; //If the password is correct
    }

    /**
     * This method take the username given by the user and check it against the DB to see if it is correct
     * @param username
     * @return
     */
    private boolean checkUsername (String username){

        return true; //If the username is correct
    }

}
