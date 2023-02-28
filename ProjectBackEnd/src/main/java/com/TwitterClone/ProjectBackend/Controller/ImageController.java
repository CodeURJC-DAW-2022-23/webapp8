package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Controller
public class ImageController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private TweetService tweetService;

    @GetMapping("/{id}/profile-pic")
    public ResponseEntity<Object> downloadProfilePic(@PathVariable long id) throws SQLException {
        Optional<User> user = profileService.findById(id);

        if (user.isPresent() && user.get().getProfilePicture() != null) {

            Resource file = new InputStreamResource(user.get().getProfilePicture().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(user.get().getProfilePicture().length()).body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    


}
