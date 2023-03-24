package com.TwitterClone.ProjectBackend.Controller.RestController;

import com.TwitterClone.ProjectBackend.Model.Hashtag;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.NotificationService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class RestProfileController {
    @Autowired
    private TweetService tweetService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private InformationManager informationManager;
    @Autowired
    private NotificationService notificationService;

    interface Basic extends User.Profile {
    }

    @Operation(summary = "Get a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Basic.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/{username}")
    @JsonView(SearchRestController.Basic.class)
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = this.profileService.findByUsername(username);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.get(), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get some followed users of a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followed Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Basic.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/followed/{username}")
    @JsonView(SearchRestController.Basic.class)
    public ResponseEntity<List<User>> getFollowed(@PathParam("from") int from,
                                                  @PathParam("size") int size,
                                                  @PathVariable String username) {
        Optional<User> user = this.profileService.findByUsername(username);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<User> followed = profileService.getFollowed(user.get().getId(), from, size);
        return new ResponseEntity<>(followed, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get some followers users of a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followed Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Basic.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/followers/{username}")
    @JsonView(SearchRestController.Basic.class)
    public ResponseEntity<List<User>> getFollowers(@PathParam("from") int from,
                                                   @PathParam("size") int size,
                                                   @PathVariable String username) {
        Optional<User> user = this.profileService.findByUsername(username);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<User> followed = profileService.getFollowers(user.get().getId(), from, size);
        return new ResponseEntity<>(followed, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Update the profile pic associated to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile pic updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/updateProfilePicture/{id}")
    @JsonView(SearchRestController.Basic.class)
    public ResponseEntity<Object> updateProfilePic(@PathVariable long id,
                                                   @RequestParam("file") MultipartFile profilePic) throws IOException, SQLException {
        Optional<User> user = profileService.findById(id);

        if (user.isPresent()) {
            this.profileService.updateProfilePic(id, profilePic);

            Resource file = new InputStreamResource(user.get().getProfilePicture().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(user.get().getProfileBanner().length()).body(file);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update the profile banner associated to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile banner updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/updateProfileBanner/{id}")
    @JsonView(SearchRestController.Basic.class)
    public ResponseEntity<Object> updateProfileBanner(@PathVariable long id,
                                                      @RequestParam("file") MultipartFile profileBanner) throws IOException, SQLException {
        Optional<User> user = profileService.findById(id);

        if (user.isPresent()) {
            this.profileService.updateProfileBanner(id, profileBanner);

            Resource file = new InputStreamResource(user.get().getProfileBanner().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(user.get().getProfileBanner().length()).body(file);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update the nickname associated to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nickname updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Basic.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/updateNickname/{id}")
    @JsonView(SearchRestController.Basic.class)
    public ResponseEntity<User> updateNickname(@PathVariable long id,
                                               @RequestParam("nickname") String nick) {
        Optional<User> user = profileService.findById(id);

        if (user.isPresent()) {
            this.profileService.updateNickname(id, nick);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update the biography associated to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biography updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Profile.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/updateBiography/{id}")
    @JsonView(SearchRestController.Basic.class)
    public ResponseEntity<User> updateBiography(@PathVariable long id,
                                                @RequestParam("biography") String bio) {
        Optional<User> user = profileService.findById(id);

        if (user.isPresent()) {
            this.profileService.updateBio(id, bio);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Follow or unfollow a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User followed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Profile.class))
            }),
            @ApiResponse(responseCode = "202", description = "User unfollowed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Profile.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/toggleFollow/{id}")
    @JsonView(SearchRestController.Basic.class)
    public ResponseEntity<List<User>> toggleFollow(@PathVariable Long id,
                                                   HttpServletRequest request) {
        Optional<User> profileUser = this.profileService.findById(id);
        if (profileUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User currentUser = this.informationManager.getCurrentUser(request);
        boolean hasFollowed = this.profileService.toggleFollow(profileUser.get(), currentUser);

        if (hasFollowed) {
            this.notificationService.createNotification(null, profileUser.get(), currentUser, "FOLLOW");
            return new ResponseEntity<>(HttpStatus.OK);
        }

        this.notificationService.deleteNotification(null,
                currentUser.getId(), "FOLLOW", profileUser.get().getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

