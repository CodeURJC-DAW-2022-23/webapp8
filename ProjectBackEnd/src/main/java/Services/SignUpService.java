package Services;

import Model.RegisteredUser;
import Model.User;
import Repository.UserDataAccessService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is on charge of implementing all the model part of the signUp process and contains all its logic.
 */
public class SignUpService {

    private UserDataAccessService userDao;

    /**
     * This method process a given form
     * @param form
     * @return A new user from the information provided.
     */
    public User createNewUser(ArrayList<HashMap<String, String>> form){
        return (User) new RegisteredUser();
    }

    /**
     * This method add a new user to our DB
     * @param newUser
     */
    private void saveNewUser(User newUser){

    }



}
