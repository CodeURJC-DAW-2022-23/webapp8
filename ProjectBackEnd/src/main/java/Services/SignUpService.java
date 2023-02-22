package Services;


import DTO.RegisteredRequest;
import Security.EmailValidator;
import UserManagement.User;
import Repository.UserRepository;
import UserManagement.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * This class is on charge of implementing all the model part of the signUp process and contains all its logic.
 */
@Service
@AllArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Transactional
    public String signup(RegisteredRequest registeredRequest){
        User user = new User();
        boolean userExist = userRepository.findByUsername(user.getUsername()).isPresent();
        if (userExist){
            throw new IllegalStateException("Username already taken");
        }
        user.setMail(registeredRequest.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return "It Works!!!!!!"; //Just for testing
    }



}
