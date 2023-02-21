package Controller;

import Services.SignUpService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is on charge of managing all the petitions from the view to the model in the signUp process.
 */
public class UserSignUpController {

    private SignUpService userSignUpService;

    /**
     * This method is on charge of adding a new user to our DB
     * @param form
     */
    public void saveNewUser(ArrayList<HashMap<String, String>> form){

    }

    /**
     * This method is on charge of processing the form of a new user
     * @param form
     * @return
     */
    public boolean processNewUserForm(ArrayList<HashMap<String, String>> form){
        return true; //Should return the result of checking the information with the secure config module
    }

}
