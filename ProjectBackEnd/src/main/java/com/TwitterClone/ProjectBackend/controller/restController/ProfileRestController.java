package com.TwitterClone.ProjectBackend.controller.restController;

import com.TwitterClone.ProjectBackend.model.mustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.model.mustacheObjects.JSONString;
import com.TwitterClone.ProjectBackend.model.mustacheObjects.UserInformation;
import com.TwitterClone.ProjectBackend.service.NotificationService;
import com.TwitterClone.ProjectBackend.service.ProfileService;
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

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ProfileRestController {
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
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserInformation.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/users/{username}")
    @JsonView(Basic.class)
    public ResponseEntity<UserInformation> getUserByUsername(@PathVariable String username) {
        Optional<User> user = this.profileService.findByUsername(username);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserInformation currentUser = this.informationManager.prepareUserInformation(user.get(), null);

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @Operation(summary = "Get some followed users of a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followed Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserInformation.class))
            }),
            @ApiResponse(responseCode = "204", description = "No more followed users found", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("users/{username}/followed")
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

        if (listFollowed.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listFollowed, HttpStatus.OK);
    }

    @Operation(summary = "Get some followers users of a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followed Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserInformation.class))
            }),
            @ApiResponse(responseCode = "204", description = "No more followers found", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("users/{username}/followers")
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

        if (listFollowers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listFollowers, HttpStatus.OK);
    }

    @Operation(summary = "Update the profile pic associated to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile pic updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            }),
            @ApiResponse(responseCode = "202", description = "Empty profile picture", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not allowed", content = @Content)
    })
    @PutMapping("users/{id}/user-image")
    @JsonView(Basic.class)
    public ResponseEntity<Object> updateProfilePic(@PathVariable long id,
                                                   @RequestParam("file") MultipartFile profilePic,
                                                    HttpServletRequest request) throws IOException {
        Optional<User> user = profileService.findById(id);
        User currentUser = this.informationManager.getCurrentUser(request);

        if (user.isPresent()) {

            if (!user.get().getId().equals(currentUser.getId())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            this.profileService.updateProfilePic(id, profilePic);

            if (profilePic.isEmpty()) {
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
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not allowed", content = @Content)
    })
    @PutMapping("users/{id}/banner-image")
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

            if (profileBanner.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }

            this.profileService.updateProfileBanner(id, profileBanner);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update the nickname associated to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nickname updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserInformation.class))
            }),
            @ApiResponse(responseCode = "400", description = "Empty nickname", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not allowed", content = @Content)
    })
    @PutMapping("users/{id}/nickname")
    @JsonView(Basic.class)
    public ResponseEntity<UserInformation> updateNickname(@PathVariable long id,
                                                          @RequestBody JSONString nick,
                                               HttpServletRequest request) {
        Optional<User> user = profileService.findById(id);
        User currentUser = this.informationManager.getCurrentUser(request);

        if (user.isPresent()) {
            
            if (!user.get().getId().equals(currentUser.getId())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            if (nick.getText().equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            this.profileService.updateNickname(id, nick.getText());
            UserInformation updatedUser = this.informationManager.prepareUserInformation(user.get(), null);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update the biography associated to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biography updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserInformation.class))
            }),
            @ApiResponse(responseCode = "403", description = "Not allowed", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("users/{id}/biography")
    @JsonView(Basic.class)
    public ResponseEntity<UserInformation> updateBiography(@RequestBody JSONString bio,
                                                           @PathVariable long id,
                                                           HttpServletRequest request) {
        Optional<User> user = profileService.findById(id);
        User currentUser = this.informationManager.getCurrentUser(request);

        if (user.isPresent()) {

            if (!user.get().getId().equals(currentUser.getId())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            this.profileService.updateBio(id, bio.getText());
            UserInformation updatedUser = this.informationManager.prepareUserInformation(user.get(), null);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Follow or unfollow a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User followed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserInformation.class))
            }),
            @ApiResponse(responseCode = "202", description = "User unfollowed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserInformation.class))
            }),
            @ApiResponse(responseCode = "403", description = "Cannot follow", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("users/{id}/followers")
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

