package com.TwitterClone.ProjectBackEnd.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This class is on charge of controlling the navigation through the website.
 */
@Controller
public class NavigationController {

    @GetMapping("/home")
    public String bruh() {
        return "home";
    }
}
