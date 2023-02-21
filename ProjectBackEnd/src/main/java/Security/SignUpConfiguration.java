package Security;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is in charge of doing the security operations related to the signup process
 */
public class SignUpConfiguration {

    /**
     * This method process each field of the given form and return a new one after securing all the data within
     * @param form
     * @return
     */
    public ArrayList<HashMap<String, String>> processNewUserForm(ArrayList<HashMap<String, String>> form){
        return new ArrayList<HashMap<String, String>>(); //Return the form data after processing it
    }

    /**
     * This method encrypts the password provided by the user and return an encrypted version of it
     * @param plaintextPassword
     * @return
     */
    public String encryptPassword(String plaintextPassword){
        return ""; //Return the password after encrypting it with bcrypt
    }

}
