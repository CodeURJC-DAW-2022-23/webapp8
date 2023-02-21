package Security;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is in charge of controlling the security part of the login procedure by using Spring security modules
 */

public class LogInConfiguration {

    /**
     * This method reads the given form and check each field to be sure that the given information is correct
     * @param form
     * @return
     */
    public ArrayList<HashMap<String, String>> processLogInForm(ArrayList<HashMap<String, String>> form){
        return new ArrayList<HashMap<String, String>>(); // Return the form after processing it
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
