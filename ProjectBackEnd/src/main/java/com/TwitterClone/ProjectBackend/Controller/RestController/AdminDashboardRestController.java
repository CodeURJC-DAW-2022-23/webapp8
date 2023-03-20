package com.TwitterClone.ProjectBackend.Controller.RestController;


import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AdminDashboardRestController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private InformationManager informationManager;

    interface Basic extends User.Basic{};


    @Operation(summary = "If the user is a admin, he can ban another user according to his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Banned", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    @PutMapping("/ban/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<Object> ban(@PathVariable Long id,
                                      HttpServletRequest request){
        User currentUser = this.informationManager.getCurrentUser(request);
        if (profileService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        User user = this.profileService.findById(id).get();
            if(currentUser.getRole().toString().equals("ADMIN")){
                user.setType("BANNED");
                user.setEnabled(false);
                this.profileService.updateUserBan(user);
                return ResponseEntity.ok(user);
            }
        return ResponseEntity.badRequest().build();
    }


    @Operation(summary = "If the user is a admin, he can unban another user according to his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Banned", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    @PutMapping("/unban/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<Object> unban(@PathVariable Long id,
                        HttpServletRequest request){
        User currentUser = this.informationManager.getCurrentUser(request);
        if (profileService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        User user = this.profileService.findById(id).get();
        if(currentUser.getRole().toString().equals("ADMIN")) {
            user.setType("PUBLIC");
            user.setEnabled(true);
            this.profileService.updateUserBan(user);
             return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "If the user is a admin, he can verify another user according to his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Banned", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    @PutMapping("/verify/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<Object> verify(@PathVariable Long id,
                         HttpServletRequest request){
        User currentUser = this.informationManager.getCurrentUser(request);
        if (profileService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
            User user = this.profileService.findById(id).get();
            if(currentUser.getRole().toString().equals("ADMIN")) {
                user.setType("VERIFIED");
                this.profileService.updateUserBan(user);
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.badRequest().build();
        }
    @Operation(summary = "If the user is a admin, he can unverify another user according to his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Banned", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    @PutMapping("/unverify/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<Object> unverify(@PathVariable Long id,
                           HttpServletRequest request){
        User currentUser = this.informationManager.getCurrentUser(request);
        if (profileService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
            User user = this.profileService.findById(id).get();
            if(currentUser.getRole().toString().equals("ADMIN")) {
                user.setType("PUBLIC");
                this.profileService.updateUserBan(user);
                return ResponseEntity.ok(user);
            }
         return ResponseEntity.badRequest().build();
    }

}
