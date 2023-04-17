package com.TwitterClone.ProjectBackend.controller.restController;


import com.TwitterClone.ProjectBackend.model.mustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.model.mustacheObjects.UserInformation;
import com.TwitterClone.ProjectBackend.service.MailService;
import com.TwitterClone.ProjectBackend.service.ProfileService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdminDashboardRestController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private InformationManager informationManager;
    @Autowired
    private MailService mailService;

    interface Basic extends User.Basic, UserInformation.Basic{}

    @Operation(summary = "If the user is a admin, he can ban another user according to his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Banned", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    @PutMapping("users/{id}/banned-users")
    @JsonView(Basic.class)
    public ResponseEntity<Object> ban(@PathVariable Long id,
                                      HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        User currentUser = this.informationManager.getCurrentUser(request);

        if (profileService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = this.profileService.findById(id).get();

        if (currentUser.getRole().toString().equals("ADMIN")) {
            user.setType("BANNED");
            user.setEnabled(false);
            this.profileService.updateUserBan(user);
            mailService.sendBanMail(user.getEmail());
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }


    @Operation(summary = "If the user is a admin, he can unban another user according to his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Banned", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    @PutMapping("users/{id}/unbanned-users")
    @JsonView(Basic.class)
    public ResponseEntity<Object> unban(@PathVariable Long id,
                                        HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        User currentUser = this.informationManager.getCurrentUser(request);

        if (profileService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = this.profileService.findById(id).get();

        if (currentUser.getRole().toString().equals("ADMIN")) {
            user.setType("PUBLIC");
            user.setEnabled(true);
            this.profileService.updateUserBan(user);
            mailService.sendUnBanMail(user.getEmail());
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "If the user is a admin, he can verify another user according to his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Banned", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    @PutMapping("users/{id}/verified-users")
    @JsonView(Basic.class)
    public ResponseEntity<Object> verify(@PathVariable Long id,
                                         HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);

        if (profileService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = this.profileService.findById(id).get();

        if (currentUser.getRole().toString().equals("ADMIN")) {
            user.setType("VERIFIED");
            this.profileService.updateUserBan(user);
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "If the user is a admin, he can unverify another user according to his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Banned", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    @PutMapping("users/{id}/unverified-users")
    @JsonView(Basic.class)
    public ResponseEntity<Object> unverify(@PathVariable Long id,
                                           HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);

        if (profileService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = this.profileService.findById(id).get();

        if (currentUser.getRole().toString().equals("ADMIN")) {
            user.setType("PUBLIC");
            this.profileService.updateUserBan(user);
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "If the user is a admin, he can get the statistics of new accounts created in the last 5 days whit new accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics shown", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "403", description = "No permission", content = @Content)
    })
    @GetMapping("users/statistics")
    public ResponseEntity<Map<String, String>> getNewAccountStatistics(HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);
        List<Tuple> statics = this.profileService.getStatics();
        Map<String, String> statistics = new HashMap<>();

        for (Tuple aStatic : statics) {
            String key = aStatic.get("join_date").toString();
            String value = aStatic.get("new_people").toString();
            statistics.put(key, value);
        }

        if (currentUser.getRole().toString().equals("ADMIN")) {
            return ResponseEntity.ok(statistics);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Operation(summary = "If the user is a admin, he can update user type user according to his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User type updated", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Type not valid", content = @Content),
            @ApiResponse(responseCode = "403", description = "User can not do that", content = @Content),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    @PutMapping("users/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<Object> toggleType(@PathVariable Long id,
                                           @RequestParam("type") String type,
                                           HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);

        if (currentUser == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (profileService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = this.profileService.findById(id).get();

        if (currentUser.getRole().toString().equals("ADMIN")) {
            switch (type){
                case "VERIFY": user.setType("VERIFIED"); break;
                case "UNVERIFY": user.setType("PUBLIC"); break;
                case "BAN": user.setType("BANNED");
                            user.setEnabled(false);
                            break;
                case "UNBAN": user.setType("PUBLIC");
                              user.setEnabled(true);
                              break;
                default: return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            this.profileService.updateUserBan(user);
            return ResponseEntity.ok(user);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Operation(summary = "Get all the users that can be verified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users obtained", content = {
                    @Content(mediaType = "application/json")
            })})
    @GetMapping("users-to-verify")
    @JsonView(Basic.class)
    public ResponseEntity<List<UserInformation>> getUsersToVerify() {
        List<User> userToVerify = this.profileService.getToVerified();
        List<UserInformation> users = this.informationManager.prepareListUser(userToVerify);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get all the verificated users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users obtained", content = {
                    @Content(mediaType = "application/json")
            })})
    @GetMapping("verificated-users")
    @JsonView(Basic.class)
    public ResponseEntity<List<UserInformation>> getVerificatedUsers() {
        List<User> userToVerify = this.profileService.getVerified();
        List<UserInformation> users = this.informationManager.prepareListUser(userToVerify);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get all the banned users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users obtained", content = {
                    @Content(mediaType = "application/json")
            })})
    @GetMapping("banned-users")
    @JsonView(Basic.class)
    public ResponseEntity<List<UserInformation>> getBannedUsers() {
        List<User> userToVerify = this.profileService.getBanned();
        List<UserInformation> users = this.informationManager.prepareListUser(userToVerify);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
