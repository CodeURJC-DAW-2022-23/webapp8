package com.TwitterClone.ProjectBackend.Controller.RestController;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.UserInformation;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/profile")
public class RestProfileController {
    @Autowired
    private TweetService tweetService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private InformationManager informationManager;
    @Autowired
    private NotificationService notificationService;

    interface Basic extends User.Profile, UserInformation.Basic {
    }

    @Operation(summary = "Get a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Basic.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/{username}")
    @JsonView(Basic.class)
    public ResponseEntity<UserInformation> getUserByUsername(@PathVariable String username) {
        Optional<User> user = this.profileService.findByUsername(username);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserInformation currentUser = this.informationManager.prepareUserInformation(user.get(), null);

        return new ResponseEntity<>(currentUser, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get some followed users of a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followed Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Basic.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/followed/{username}")
    @JsonView(Basic.class)
    public ResponseEntity<List<UserInformation>> getFollowed(@PathParam("from") int from,
                                                  @PathParam("size") int size,
                                                  @PathVariable String username) {
        Optional<User> user = this.profileService.findByUsername(username);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<User> followed = profileService.getFollowed(user.get().getId(), from, size);
        List<UserInformation> listFollowed = this.informationManager.prepareListUser(followed);
        return new ResponseEntity<>(listFollowed, HttpStatus.OK);
    }

    @Operation(summary = "Get some followers users of a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followed Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Basic.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/followers/{username}")
    @JsonView(Basic.class)
    public ResponseEntity<List<UserInformation>> getFollowers(@PathParam("from") int from,
                                                   @PathParam("size") int size,
                                                   @PathVariable String username) {
        Optional<User> user = this.profileService.findByUsername(username);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<User> followers = profileService.getFollowers(user.get().getId(), from, size);
        List<UserInformation> listFollowers = this.informationManager.prepareListUser(followers);
        return new ResponseEntity<>(listFollowers, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Update the profile pic associated to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile pic updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            }),
            @ApiResponse(responseCode = "202", description = "Empty profile picture", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/updateProfilePicture/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<Object> updateProfilePic(@PathVariable long id,
                                                   @RequestParam("file") MultipartFile profilePic,
                                                    HttpServletRequest request) throws IOException, SQLException {
        Optional<User> user = profileService.findById(id);
        User currentUser = this.informationManager.getCurrentUser(request);
        if (user.isPresent()) {
            if (!user.get().getId().equals(currentUser.getId())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            this.profileService.updateProfilePic(id, profilePic);

            if (!profilePic.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update the profile banner associated to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile banner updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            }),
            @ApiResponse(responseCode = "202", description = "Empty profile banner", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/updateProfileBanner/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<Object> updateProfileBanner(@PathVariable long id,
                                                      @RequestParam("file") MultipartFile profileBanner,
                                                      HttpServletRequest request) throws IOException {
        Optional<User> user = profileService.findById(id);
        User currentUser = this.informationManager.getCurrentUser(request);
        if (user.isPresent()) {
            if (!user.get().getId().equals(currentUser.getId())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            this.profileService.updateProfileBanner(id, profileBanner);

            if (!profileBanner.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update the nickname associated to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nickname updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.Basic.class))
            }),
            @ApiResponse(responseCode = "202", description = "Empty nickname", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/updateNickname/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<UserInformation> updateNickname(@PathVariable long id,
                                               @RequestParam("nickname") String nick,
                                               HttpServletRequest request) {
        Optional<User> user = profileService.findById(id);
        User currentUser = this.informationManager.getCurrentUser(request);

        if (user.isPresent()) {
            if (!user.get().getId().equals(currentUser.getId())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            this.profileService.updateNickname(id, nick);
            UserInformation updatedUser = this.informationManager.prepareUserInformation(user.get(), null);

            if (nick.equals("")) {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
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
    @JsonView(Basic.class)
    public ResponseEntity<UserInformation> updateBiography(@PathVariable long id,
                                                @RequestParam("biography") String bio,
                                                           HttpServletRequest request) {
        Optional<User> user = profileService.findById(id);

        User currentUser = this.informationManager.getCurrentUser(request);
        if (user.isPresent()) {
            if (!user.get().getId().equals(currentUser.getId())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            this.profileService.updateBio(id, bio);
            UserInformation updatedUser = this.informationManager.prepareUserInformation(user.get(), null);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
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
    @JsonView(Basic.class)
    public ResponseEntity<List<UserInformation>> toggleFollow(@PathVariable Long id,
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

