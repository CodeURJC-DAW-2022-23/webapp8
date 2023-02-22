package Services;


import DTO.RegisteredRequest;
import UserManagement.User;
import Repository.UserRepository;
import lombok.AllArgsConstructor;
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

    @Transactional
    public void signup(RegisteredRequest registeredRequest){
        User user = new User();
        user.setMail(registeredRequest.getEmail());
        user.setUsername(registeredRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registeredRequest.getPassword()));

        userRepository.save(user);

    }



}
