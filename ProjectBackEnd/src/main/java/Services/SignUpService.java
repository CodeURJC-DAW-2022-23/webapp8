package Services;


import DTO.RegisteredRequest;
import Security.EmailValidator;
import UserManagement.User;
import Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * This class is on charge of implementing all the model part of the signUp process and contains all its logic.
 */
@Service
@AllArgsConstructor
public class SignUpService {

    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    /**
     * It takes the parameters of the request, test if they are valid and them registered a new user
     * @param request
     * @return
     */
    @Transactional
    public String signup(RegisteredRequest request){
       User user =  new User(request.getUsername(), request.getPassword(), request.getEmail());
        if (!emailValidator.test(request.getEmail())){
            throw new IllegalStateException("Invalid Mail Format");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("Username already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        userRepository.save(user);

        return  "It worked!";
    }
}
