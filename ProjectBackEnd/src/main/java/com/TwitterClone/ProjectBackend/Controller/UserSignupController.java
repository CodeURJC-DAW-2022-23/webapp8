package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.DTO.RegisteredRequest;
import com.TwitterClone.ProjectBackend.userManagement.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * This class is on charge of managing all the petitions from the view to the model in the signUp process.
 */
@Controller
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupController {
    @Autowired
    private UserService service;



    @GetMapping("/signup")
    public String signup(Model model, HttpServletRequest request){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup (@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String email)
                            throws MessagingException, UnsupportedEncodingException {
        RegisteredRequest registeredRequest = new RegisteredRequest(email, username, password);
        service.signup(registeredRequest);

        return "confirmation";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {

        if (service.verify(code)) {
            return "verify";
        }

        return "error";
    }
}