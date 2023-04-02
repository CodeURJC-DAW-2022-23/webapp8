package com.TwitterClone.ProjectBackend.controller.restController;

import com.TwitterClone.ProjectBackend.model.Hashtag;
import com.TwitterClone.ProjectBackend.model.Tweet;
import com.TwitterClone.ProjectBackend.service.HashtagService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.TwitterClone.ProjectBackend.userManagement.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private HashtagService hashtagService;

    interface Basic extends User.Basic,Tweet.Basic,Hashtag.Basic{}

    ;

    @Operation(summary = "Find profiles which contains the keyword in their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles Found", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "No profiles found that match the keyword", content = @Content)
    })
    @GetMapping("/users/{keyword}/found-users")
    @JsonView(Basic.class)
    public ResponseEntity<Object> SearchProfiles(@PathVariable String keyword) {
        List<User> list = userService.findByUsernameContainingIgnoreCase(keyword);

        if (list.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Find Hashtags which contains the keyword in their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles Found", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "No hashtags found that match the keyword", content = @Content)
    })
    @GetMapping("/hashtags/{keyword}/found-hashtags")
    @JsonView(Basic.class)
    public ResponseEntity<Object> SearchHashtags(@PathVariable String keyword) {
        List<Hashtag> list = hashtagService.findByHashtagIsContainingIgnoreCase(keyword);

        if (list.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(list);
    }

}
