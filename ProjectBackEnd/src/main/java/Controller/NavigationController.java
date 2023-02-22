package com.TwitterClone.ProjectBackEnd.Controller;

import Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is on charge of controlling the navigation through the website.
 */
@Controller
public class NavigationController {

    Explorer explorer;
    Bookmark bookmark;
    Home home;
    Profile profile;
    Notification notification;

    @GetMapping("/home")
    public String toHome() {
        return "home";
    }

    @GetMapping("/explore")
    public String toExplore(Model model) {

        this.explorer = new Explorer();

        List<Trend> trends = this.explorer.getTrends();

        model.addAttribute("trends", trends);

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
