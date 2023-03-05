package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;
import java.util.UUID;

@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private InformationManager informationManager;

    @PostMapping("/profile/edit-profile")
    public String editProfile(HttpServletRequest request,
                              @RequestParam MultipartFile banner,
                              @RequestParam MultipartFile profile,
                              @RequestParam String nickname,
                              @RequestParam String biography) throws IOException {
        User currentUser = this.informationManager.getCurrentUser(request);
        this.profileService.uploadProfile(currentUser, banner, profile, nickname, biography);
        return "redirect:/profile/" + currentUser.getId().toString();
    }

}
