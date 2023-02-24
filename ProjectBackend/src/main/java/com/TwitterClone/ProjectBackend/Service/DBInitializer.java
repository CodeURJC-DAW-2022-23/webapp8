package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.TweetRepository;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DBInitializer {
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.pass}")
    String adminPass;

    @PostConstruct
    public void init() throws IOException, URISyntaxException {

        //Sample Users
        String [] files = {"example_data/elrubius_profilepic.jpg","example_data/elrubius_profilebanner.jpg"};
        User user1 = new User("@Rubiu5","elrubius","Rata Noruega.  Me gustan los gatos obesos.","rubius@gmail.com","rubius",files, LocalDate.of(2013,10,13),"VERIFIED");
        files = new String[]{"example_data/KOI_KEYLAND_profilepic.jpg", "example_data/KOI_KEYLAND_profilebanner.jpg"};
        User user2 = new User("@Keyland71", "KOI KEYLAND71", "19 y/o\nRocket League proplayer for @KOI", "example2@gmail.com", "examplePassword2",files, LocalDate.of(2018,4,21), "USER");
        files = new String[]{"example_data/Alanis_profilepic.jpg", "example_data/Alanis_profilebanner.jpg"};
        User user3 = new User("@antonioalanxs", "Alanís",  "",  "example3@gmail.com", "examplePassword3",files, LocalDate.of(2019,8,7), "PRIVATE");
        files = new String[]{"example_data/Ibai_profilepic.jpg", "example_data/Ibai_profilebanner.jpg"};
        User user4 = new User("@ibai","Ibai","Sigue a nuestros equipos @KOI y @PorcinosFC, http://twitch.tv/ibai","ibai@gmail.com","ibai",files, LocalDate.of(2014,8,5), "VERIFIED");
        userRepository.save(user1);

        User testUser = new User("user", passwordEncoder.encode("pass"),"user@mail.com", "USER");
        userRepository.save(testUser);

        User admin = new User("admin", adminPass, "admin@gmail.com", "ADMIN");
        userRepository.save(admin);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        List<User> users = userRepository.findAll();
        //Sample Tweets
        //@ibai
        Blob[] images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet5 = new Tweet("Devastado, afligido, descorazonado, atormentado, apenado, entristecido, desolado, triste, cabizbajo, lloroso, cariacontecido, compungido, destruido, mustio, apesadumbrado, deshecho, demolido.", users.get(3), LocalDateTime.of(2023,02,14,20,00,00), null, images);

        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet6 = new Tweet("Chup chup con la #KingsLeague", users.get(3), LocalDateTime.of(2023,02,14,21,00,00), null, images);

        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet10 = new Tweet("Creéis que lo de Shakira iba por Piqué?", users.get(3), LocalDateTime.of(2023,01,12,1,55,05), null, images);
        tweetRepository.save(tweet6);
        tweetRepository.save(tweet10);
        tweetRepository.save(tweet5);

        List<Tweet> tweets = tweetRepository.findAll();
        //@Rubiu5
        tweet5 = tweets.get(tweets.size()-1);
        Resource image1 = new ClassPathResource("example_data/tweet3_1.jpg");
        Resource image2 = new ClassPathResource("example_data/tweet3_2.jpg");
        Resource image3 = new ClassPathResource("example_data/tweet3_3.jpg");
        images = new Blob[] {
                BlobProxy.generateProxy(image1.getInputStream(),image1.contentLength()),
                BlobProxy.generateProxy(image2.getInputStream(),image2.contentLength()),
                BlobProxy.generateProxy(image3.getInputStream(),image3.contentLength()),
                null
        };
        Tweet tweet4 = new Tweet("Deja de llorar", users.get(0), LocalDateTime.of(2023,02,19,20,00,00), tweet5, images);

        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet3 = new Tweet("Hoy he hecho autostop en un pueblo alejado de la mano de dios en Japón y me he montado con el perro mas majo del mundo 犬", users.get(0), LocalDateTime.of(2023,02,14,20,30,05), null, images);
        tweetRepository.save(tweet4);
        tweetRepository.save(tweet3);
        //@Keyland71
        image1 = new ClassPathResource("example_data/tweet1.jpg");
        images = new Blob[] {
                BlobProxy.generateProxy(image1.getInputStream(),image1.contentLength()),
                null,
                null,
                null
        };
        Tweet tweet1 = new Tweet("Fotito con mi fan \uD83D\uDCAA\uD83C\uDFFD\uD83D\uDCAA\uD83C\uDFFD\uD83D\uDD25\uD83D\uDD25", users.get(1), LocalDateTime.of(2022,06,14,16,03,00),null, images);

        image1 = new ClassPathResource("example_data/tweet2_1.jpg");
        image2 = new ClassPathResource("example_data/tweet2_2.jpg");
        image3 = new ClassPathResource("example_data/tweet2_3.jpg");
        Resource image4 = new ClassPathResource("example_data/tweet2_4.jpg");
        images = new Blob[] {
                BlobProxy.generateProxy(image1.getInputStream(),image1.contentLength()),
                BlobProxy.generateProxy(image2.getInputStream(),image2.contentLength()),
                BlobProxy.generateProxy(image3.getInputStream(),image3.contentLength()),
                BlobProxy.generateProxy(image4.getInputStream(),image4.contentLength())
        };
        Tweet tweet2 = new Tweet("MarvelsSpiderManMilesMorales", users.get(1), LocalDateTime.of(2021,8,16,20,00,00), null, images);

        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet9 = new Tweet("no titiritititiiiiii", users.get(1), LocalDateTime.of(2023,02,20,14,9,00), null, images);
        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);
        tweetRepository.save(tweet9);

        //@antonioalanxs
        image1 = new ClassPathResource("example_data/tweet7.jpg");
        images = new Blob[] {
                BlobProxy.generateProxy(image1.getInputStream(),image1.contentLength()),
                null,
                null,
                null
        };
        Tweet tweet7 = new Tweet("En primaria deberían poner una asignatura de coger aceitunas @Keyland71", users.get(2), LocalDateTime.of(2023,02,20,14,03,00), null, images);

        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet8 = new Tweet("Tienes razón \uD83E\uDD75\uD83E\uDD75", users.get(2), LocalDateTime.of(2023,02,20,14,04,00), null, images);
        tweetRepository.save(tweet7);
        tweetRepository.save(tweet8);

        /*tweets = tweetRepository.findAll();
        users.get(3).addTweet(tweets.get(0));
        users.get(3).addTweet(tweets.get(1));
        users.get(3).addTweet(tweets.get(2));
        users.get(0).addTweet(tweets.get(3));
        users.get(0).addTweet(tweets.get(4));
        users.get(1).addTweet(tweets.get(5));
        users.get(1).addTweet(tweets.get(6));
        users.get(1).addTweet(tweets.get(7));
        users.get(2).addTweet(tweets.get(9));
        users.get(2).addTweet(tweets.get(9));
        userRepository.save(users.get(0));
        userRepository.save(users.get(1));
        userRepository.save(users.get(2));
        userRepository.save(users.get(3));*/
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
