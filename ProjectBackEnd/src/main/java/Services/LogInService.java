package Services;

import Model.RegisteredUser;
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
    public RegisteredUser obtainActualUser(){
        return  new RegisteredUser();
    }


}
