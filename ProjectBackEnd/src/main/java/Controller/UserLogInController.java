package Controller;

import Services.LogInService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is on charge of controlling all events affecting the log In process by taking the request of the view and
 * sending then to the model (LogInService) to retrieve all the necessary information.
 */
public class UserLogInController {

    private LogInService userLogInService;

    /**
     * This method process the form that have been shown in the view
     * @param form
     */
    public void processUserLogInForm(ArrayList<HashMap<String, String>> form){

    }

    /**
     * This method send the form, so it can be checked against the DB
     * @param form
     * @return
     */
    public boolean sendUserLogInForm (ArrayList<HashMap<String, String>> form){
        return true;// true if the form have been corretly send and receive
    }

    /**
     * This private method check the information of the form before sending it to the DB
     * @param form
     * @return
     */
    private boolean checkFormDataAuthenticity(ArrayList<HashMap<String, String>> form){
        return true; // true if security test have been successful
    }

}
