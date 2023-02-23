package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.TweetRepository;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class DBInitializer {
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() throws IOException, URISyntaxException {

        //Sample Users
        String [] files = new String[]{"example_data/KOI_KEYLAND_profilepic.jpg", "example_data/KOI_KEYLAND_profilebanner.jpg"};
        User user2 = new User("@Keyland71", "KOI KEYLAND71", "19 y/o\nRocket League proplayer for @KOI", "example2@gmail.com", "examplePassword2",files, LocalDate.of(2018,4,21), "Registered");
        userRepository.save(user2);
        //Sample Tweets
        List<User> u = userRepository.findAll();
        Blob[] images = new Blob[1];
        Tweet tweet1 = new Tweet("Fotito con mi fan \uD83D\uDCAA\uD83C\uDFFD\uD83D\uDCAA\uD83C\uDFFD\uD83D\uDD25\uD83D\uDD25", user2, LocalDateTime.of(2022,06,14,16,03,00),null, images);
        Tweet tweet2 = new Tweet("MarvelsSpiderManMilesMorales", user2, LocalDateTime.of(2021,8,16,20,00,00), null, images);
        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);
    }
}

/*    @Autowired
    private PasswordEncoder passwordEncoder;*/

    /*@PostConstruct
    public void init() throws IOException, URISyntaxException {

        //Sample Users
        String [] files = new String[]{"example_data/KOI_KEYLAND_profilepic.jpg", "example_data/KOI_KEYLAND_profilebanner.jpg"};
        User user2 = new User("@Keyland71", "KOI KEYLAND71", "19 y/o\nRocket League proplayer for @KOI", "example2@gmail.com", "examplePassword2",files, LocalDate.of(2018,4,21), "USER");
        userRepository.save(user2);

        User testUser = new User("user", passwordEncoder.encode("pass"),"user@mail.com");
        userRepository.save(testUser);

        //Sample Tweets
        Tweet tweet1 = new Tweet("Fotito con mi fan \uD83D\uDCAA\uD83C\uDFFD\uD83D\uDCAA\uD83C\uDFFD\uD83D\uDD25\uD83D\uDD25", user2, LocalDateTime.of(2022,06,14,16,03,00), null);
        Tweet tweet2 = new Tweet("MarvelsSpiderManMilesMorales", user2, LocalDateTime.of(2021,8,16,20,00,00), null);

        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);

    }
}*/
