package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.DTO.RegisteredRequest;
import com.TwitterClone.ProjectBackend.userManagement.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * This class is on charge of managing all the petitions from the view to the model in the signUp process.
 */
@RestController
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupController {
    @Autowired
    private UserService service;

    @GetMapping("/signup")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }

    /**
     * We create a new form which name is the same as the id on the HTML file
     *
     * @return
     */
    @PostMapping("/signup")
    public ModelAndView signup (@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String email) throws MessagingException, UnsupportedEncodingException {
        RegisteredRequest registeredRequest = new RegisteredRequest(email, username, password);
        service.signup(registeredRequest);
        ModelAndView modelAndView = new ModelAndView("redirect:/confirmation.html");
        return modelAndView;
    }

    @GetMapping("/verify")
    public ModelAndView verifyUser(@Param("code") String code) {
        if (service.verify(code)) {
            ModelAndView modelAndView = new ModelAndView("redirect:/verify.html");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/error");
            return modelAndView;
        }
    }

}