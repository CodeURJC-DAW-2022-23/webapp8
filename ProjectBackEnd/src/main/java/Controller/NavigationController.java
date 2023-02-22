package com.TwitterClone.ProjectBackEnd.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This class is on charge of controlling the navigation through the website.
 */
@Controller
public class NavigationController {

    @GetMapping("/home")
    public String toHome() {
        return "home";
    }

    @GetMapping("/explore")
    public String toExplore() {
        return "explore";
    }

    @GetMapping("/notifications")
    public String toNotifications() {
        return "notifications";
    }

    @GetMapping("/bookmarks")
    public String toBookmark() {
        return "bookmarks";
    }

    @GetMapping("/profile")
    public String toProfile() {
        return "profile";
    }

    @GetMapping("/write-tweet")
    public String toWriteTweet() {
        return "write-tweet";
    }

    @GetMapping("/error")
    public String toError() {
        return "error";
    }
}
