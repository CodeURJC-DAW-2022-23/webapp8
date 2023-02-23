package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("profiles/")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public List<User> getAllUsers() {
        return profileService.findAll();
    }

    @GetMapping(path = "{id}")
    public User getOneUser(@PathVariable("id") UUID id) {
        return profileService.findById(id).orElse(null);
    }

    /*@PostMapping
    public void postUser(String username, String nickname, String biography, String mail, String password, MultipartFile banner, MultipartFile profilePic) throws IOException {
        Blob[] files = {BlobProxy.generateProxy( profilePic.getInputStream(),profilePic.getSize()), BlobProxy.generateProxy( banner.getInputStream(),banner.getSize())};
        profileService.createProfile(username, nickname, biography, mail, password, files);
    }*/
}
