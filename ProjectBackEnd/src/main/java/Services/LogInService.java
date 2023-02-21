package Services;

import Model.RegisteredUser;
import Model.User;
import Repository.UserDataAccessService;

/**
 * This class is the model part of the logIn process. It contains the low implementation of the logIn process.
 */
public class LogInService {

    private UserDataAccessService useDao;

    /**
     * This method retrieve the actual user from the DB
     * @return
     */
    public User obtainActualUser(){
        return (User) new RegisteredUser();
    }


}
