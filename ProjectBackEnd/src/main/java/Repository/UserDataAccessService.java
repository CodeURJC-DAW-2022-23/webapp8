package Repository;

import Model.RegisteredUser;
import Model.User;

import java.util.UUID;

/**
 * This class is the access point to the user database. Is on charge of safely retrieving the data from it.
 */
public class UserDataAccessService {

    public void addNewUser(User user){

    }

    public boolean deleteUser(UUID userId){
        return true; //True if it has been correctly remove from DB
    }

    public RegisteredUser retrieveUser(UUID userId){
        return  new RegisteredUser();
    }

}
