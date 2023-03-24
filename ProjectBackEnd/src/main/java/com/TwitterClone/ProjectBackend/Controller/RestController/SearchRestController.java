package com.TwitterClone.ProjectBackend.Controller.RestController;

import com.TwitterClone.ProjectBackend.Model.Hashtag;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.ModelManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.TweetInformation;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.UserInformation;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.TwitterClone.ProjectBackend.userManagement.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchRestController {

    @Autowired
    private InformationManager informationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private HashtagService hashtagService;

    interface Basic extends User.Basic, Tweet.Basic, TweetInformation.Basic, Hashtag.Basic, UserInformation.Basic {
    }

    ;

    @Operation(summary = "Find profiles which contains the keyword in their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles Found", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "No profiles found that match the keyword", content = @Content)
    })
    @GetMapping("/search/profiles/{keyword}")
    @JsonView(Basic.class)
    public ResponseEntity<Object> SearchProfiles(@PathVariable String keyword) {
        List<User> list = userService.findByUsernameContainingIgnoreCase(keyword);
        List<UserInformation> listUsers = this.informationManager.prepareListUser(list);
        //List<Hashtag> list2 = hashtagService.findByHashtagIsContainingIgnoreCase(keyword);

        if (list.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listUsers);
    }

    @Operation(summary = "Find Hashtags which contains the keyword in their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles Found", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "No hashtags found that match the keyword", content = @Content)
    })
    @GetMapping("/search/hashtags/{keyword}")
    @JsonView(Basic.class)
    public ResponseEntity<Object> SearchHashtags(@PathVariable String keyword) {
        List<Hashtag> list = hashtagService.findByHashtagIsContainingIgnoreCase(keyword);

        if (list.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(list);
    }

}
