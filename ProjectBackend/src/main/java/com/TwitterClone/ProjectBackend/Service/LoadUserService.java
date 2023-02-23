package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This service is dedicated to the management of the entity user. Specially whn loading
 * then by usiung the UserDetailsService interface form spring security
 */
@Service
@AllArgsConstructor

public class LoadUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND = "User with username %username not found";
    private final UserRepository userRepository;


    /**
     * It searchs on the repository to see if it can find a user by the username. If not, it throws an
     * exception
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).
                orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND)));
    }


}
