package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;
import java.util.UUID;

@RequestMapping("profiles/")
@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public List<User> getAllUsers() {
        return profileService.findAll();
    }

    @GetMapping(path = "{id}")
    public User getOneUser(@PathVariable("id") Long id) {
        return profileService.findById(id).orElse(null);
    }

    @GetMapping(path = "{username}")
    public User getOneUser(@PathVariable("username") String username) {
        return profileService.findByUsername(username).orElse(null);
    }

    @PostMapping("create/")
    public void postUser(String username, String mail, String password) throws IOException {
        profileService.createProfile(username, password, mail);
    }


}
