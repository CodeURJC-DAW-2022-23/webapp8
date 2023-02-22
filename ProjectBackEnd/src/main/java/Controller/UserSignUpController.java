package Controller;

import DTO.RegisteredRequest;
import Services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is on charge of managing all the petitions from the view to the model in the signUp process.
 */
@RestController
@RequestMapping("/signup")
public class UserSignUpController {

    @Autowired
    private SignUpService service;

    /**
     * We create a new form which name is the same as the id on the HTML file
     *
     * @return
     */

    @PostMapping("/signup")
    public ResponseEntity<String> signup (@RequestBody RegisteredRequest registeredRequest){
        service.signup(registeredRequest);
        return new ResponseEntity<>("ResgistrationSuccessful", HttpStatus.OK);
    }

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
