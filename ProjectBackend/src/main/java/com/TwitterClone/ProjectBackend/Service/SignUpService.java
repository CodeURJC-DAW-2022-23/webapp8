package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.DTO.RegisteredRequest;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.Security.EmailValidator;
import com.TwitterClone.ProjectBackend.userManagement.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * This class is on charge of implementing all the model part of the signUp process and contains all its logic.
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
public class SignUpService {

    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    /**
     * It takes the parameters of the request, test if they are valid and them registered a new user
     * @param request
     * @return
     */
    @Transactional
    public String signup(RegisteredRequest request){
        User user =  new User(request.getUsername(), request.getPassword(), request.getEmail(), "USER");
        if (!emailValidator.test(request.getEmail())){
            throw new IllegalStateException("Invalid Mail Format");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("Username already taken");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //user.setEnabled(true);
        userRepository.save(user);

        return  "It worked!";
    }
}