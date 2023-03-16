package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.Hashtag;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.ModelManager;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.TwitterClone.ProjectBackend.userManagement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This class is on charge of managing all request from the view regarding the search engine
 */

@Controller
public class SearchController {

    @Autowired
    private InformationManager informationManager;
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private UserService userService;

    @Autowired
    private HashtagService hashtagService;

    /**
     * Realize a petition with the asked keyword
     * @param keyword
     * @param request
     * @return
     */
    @GetMapping("/search")
    public String search(@RequestParam String keyword, HttpServletRequest request){
        return "redirect:/search/"+keyword;
    }

    /**
     * Shows all the elements relation with the keyword
     * @param keyword
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/search/{keyword}")
    public String toSearch(@PathVariable String keyword,Model model, HttpServletRequest request){
        List<User> list = userService.findByUsernameContainingIgnoreCase(keyword);
        List<Hashtag> list2 = hashtagService.findByHashtagIsContainingIgnoreCase(keyword);
        this.modelManager.addCurrentTrends(model);
        this.informationManager.addNameToThePage(model,keyword);
        this.informationManager.addProfileInfoToLeftBar(model,request);
        model.addAttribute("users",list);
        model.addAttribute("trends", list2);
        return "search";
    }
}