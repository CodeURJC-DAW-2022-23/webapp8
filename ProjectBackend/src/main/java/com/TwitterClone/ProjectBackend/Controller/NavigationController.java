package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.*;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * This class is on charge of controlling the navigation through the website.
 */

@Controller
public class NavigationController {

    @Autowired
    private HashtagService hashtagService;
    @Autowired
    private UserRepository userRepository;

    /**
     * Change from the current page to the home page
     * @param model
     * @return
     */
    @GetMapping("/home")
    public String toHome(Model model) {

        this.addCurrentTrends(model);

        return "home";
    }

    /**
     * Change from the current page to the explore page
     * @param model
     * @return
     */
    @GetMapping("/explore")
    public String toExplore(Model model) {

        this.addCurrentTrends(model);

        return "explore";
    }

    /**
     * Change from the current page to the notifications page
     * @param model
     * @return
     */
    @GetMapping("/notifications")
    public String toNotifications(Model model) {

        this.addCurrentTrends(model);

        return "notifications";
    }

    /**
     * Change from the current page to the bookmarks page
     * @param model
     * @return
     */
    @GetMapping("/bookmarks")
    public String toBookmark(Model model) {

        this.addCurrentTrends(model);

        return "bookmarks";
    }

    /**
     * Change from the current page to the profile page
     * @return
     */
    @GetMapping("/profile")
    public String toProfile() {
        return "profile";
    }

    /**
     * Change from the current page to the write tweet page
     * @return
     */
    @GetMapping("/write-tweet")
    public String toWriteTweet() {
        return "write-tweet";
    }

    /**
     * Cahnge from the current page to the error page
     * @return
     */
    @GetMapping("/error")
    public String toError() {
        return "error";
    }

    /**
     * Add to the template the current trends
     * @param model
     */
    private void addCurrentTrends(Model model) {

        Page<Trend> pageTrends = this.hashtagService.getCurrentTrends();

        List<Trend> trends = pageTrends.stream().toList();

        model.addAttribute("trends", trends);
    }
}
