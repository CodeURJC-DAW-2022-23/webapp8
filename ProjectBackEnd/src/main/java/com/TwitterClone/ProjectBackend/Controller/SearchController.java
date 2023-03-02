package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * This class is on charge of managing all request from the view regarding the search engine
 */

@Controller
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/search")
    public List<User> lookForUsers(@RequestParam String keyword) {
        List<User> list = userRepository.findByUsernameContainingIgnoreCase(keyword);
        return list;
    }
}
