package Services;

import Repository.UserRepository;
import UserManagement.User;

/**
 * This class is the model part of the logIn process. It contains the low implementation of the logIn process.
 */
public class LogInService {

    private UserRepository useDao;

    /**
     * This method retrieve the actual user from the DB
     * @return
     */
    public User obtainActualUser(){
        return  new User();
    }


}
