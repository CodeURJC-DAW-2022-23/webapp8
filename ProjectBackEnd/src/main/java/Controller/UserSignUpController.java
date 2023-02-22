package Controller;

import DTO.RegisteredRequest;
import Services.SignUpService;
import lombok.AllArgsConstructor;
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
 */@RestController
@RequestMapping(path = "/signup")
@AllArgsConstructor
public class UserSignUpController {

    private final RegisteredRequest registeredRequest;
    private final SignUpService service;


    /**
     * We create a new form which name is the same as the id on the HTML file
     *
     * @return
     */
    @PostMapping
    public ResponseEntity<String> signup (@RequestBody RegisteredRequest registeredRequest){
        service.signup(registeredRequest);
        return new ResponseEntity<>("ResgistrationSuccessful", HttpStatus.OK);
    }

}
