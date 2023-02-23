package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.DTO.RegisteredRequest;
import com.TwitterClone.ProjectBackend.Service.SignUpService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is on charge of managing all the petitions from the view to the model in the signUp process.
 */
@RestController
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupController {
    private RegisteredRequest registeredRequest;
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
}
