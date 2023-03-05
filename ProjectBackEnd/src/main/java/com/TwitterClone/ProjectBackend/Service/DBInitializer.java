package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Hashtag;
import com.TwitterClone.ProjectBackend.Model.Notification;
import com.TwitterClone.ProjectBackend.Model.Notification;
import com.TwitterClone.ProjectBackend.Model.Trend;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.HashtagRepository;
import com.TwitterClone.ProjectBackend.Repository.NotificationRepository;
import com.TwitterClone.ProjectBackend.Repository.NotificationRepository;
import com.TwitterClone.ProjectBackend.Repository.TweetRepository;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.beans.Transient;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DBInitializer {
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashtagRepository trendRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationRepository notificationRepository;

    @Value("${admin.pass}")
    String adminPass;

    @PostConstruct
    public void init() throws IOException {

        //Sample Users
        String[] files = {"example_data/elrubius_profilepic.jpg","example_data/elrubius_profilebanner.jpg"};
        User user1 = new User("Rubiu5","elrubius","Rata Noruega.  Me gustan los gatos obesos.","rubius@gmail.com",passwordEncoder.encode("rubius"), LocalDate.of(2013,10,13),"BANNED");
        user1.setImages(files);
        user1.setEnabled(false);
        files = new String[]{"example_data/KOI_KEYLAND_profilepic.jpg", "example_data/KOI_KEYLAND_profilebanner.jpg"};
        User user2 = new User("Keyland71", "KOI KEYLAND71", "19 y/o\nRocket League proplayer for @KOI", "example2@gmail.com", passwordEncoder.encode("examplePassword2"), LocalDate.of(2018,4,21), "PUBLIC");
        user2.setImages(files);
        files = new String[]{"example_data/Alanis_profilepic.jpg", "example_data/Alanis_profilebanner.jpg"};
        User user3 = new User("antonioalanxs", "Alanís",  "",  "example3@gmail.com", passwordEncoder.encode("examplePassword3"),LocalDate.of(2019,8,7), "PRIVATE");
        user3.setImages(files);
        files = new String[]{"example_data/Ibai_profilepic.jpg", "example_data/Ibai_profilebanner.jpg"};
        User user4 = new User("ibai","Ibai","Sigue a nuestros equipos @KOI y @PorcinosFC, http://twitch.tv/ibai","ibai@gmail.com",passwordEncoder.encode("ibai"), LocalDate.of(2014,8,5), "VERIFIED");
        user4.setImages(files);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        List<User> u = userRepository.findAll();
        user1 = u.get(0);
        user2 = u.get(1);
        user3 = u.get(2);
        user4 = u.get(3);

        user4.addFollower(user1);
        user1.addFollowed(user4);

        user3.addFollower(user2);
        user2.addFollowed(user3);

        user2.addFollower(user4);
        user4.addFollowed(user2);

        user1.addFollower(user3);
        user3.addFollowed(user1);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        files = new String[]{"example_data/Default_profilepic.jpg", "example_data/Default_profilebanner.jpg"};
        User testUser = new User("user", passwordEncoder.encode("pass"), "ikablue0185@gmail.com", "USER");
        testUser.setImages(files);
        testUser.setNickname("user");
        userRepository.save(testUser);
        User admin = new User("admin", adminPass, "admin@mail.com", "ADMIN");
        admin.setImages(files);
        admin.setNickname("admin");
        userRepository.save(admin);

        u = userRepository.findAll();
        testUser = u.get(4);
        user3 = u.get(2);
        testUser.addFollower(user3);
        user3.addFollowed(testUser);
        user3.addFollower(testUser);
        testUser.addFollowed(user3);
        userRepository.save(user3);
        userRepository.save(testUser);

        List<User> users = Arrays.asList(user1, user2, user3, user4,testUser, admin);
        //Sample Tweets
        //@ibai
        Blob[] images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet5 = new Tweet("Devastado, afligido, descorazonado, atormentado, apenado, entristecido, desolado, triste, cabizbajo, lloroso, cariacontecido, compungido, destruido, mustio, apesadumbrado, deshecho, demolido.", users.get(3), LocalDateTime.of(2023,02,14,20,00,00), images);

        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet6 = new Tweet("Chup chup con la #KingsLeague", users.get(3), LocalDateTime.of(2023,02,14,21,00,00), images);

        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet10 = new Tweet("Creéis que lo de Shakira iba por Piqué?", users.get(3), LocalDateTime.of(2023,01,12,1,55,05), images);
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
        Tweet tweet4 = new Tweet("Deja de llorar", users.get(0), LocalDateTime.of(2023,02,19,20,00,00), images);

        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet3 = new Tweet("Hoy he hecho autostop en un pueblo alejado de la mano de dios en Japón y me he montado con el perro mas majo del mundo 犬", users.get(0), LocalDateTime.of(2023,02,14,20,30,05), images);
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
        Tweet tweet1 = new Tweet("Fotito con mi fan \uD83D\uDCAA\uD83C\uDFFD\uD83D\uDCAA\uD83C\uDFFD\uD83D\uDD25\uD83D\uDD25", users.get(1), LocalDateTime.of(2022,06,14,16,03,00), images);

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
        Tweet tweet2 = new Tweet("#MarvelsSpiderManMilesMorales", users.get(1), LocalDateTime.of(2021,8,16,20,00,00), images);

        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet9 = new Tweet("no titiritititiiiiii", users.get(1), LocalDateTime.of(2023,02,20,14,9,00), images);
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
        Tweet tweet7 = new Tweet("En primaria deberían poner una asignatura de coger aceitunas @Keyland71", users.get(2), LocalDateTime.of(2023,02,20,14,03,00), images);

        //@user
        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet11 = new Tweet("#KingsLeague #Tailwind #RCLS #Pokemon #Grupo8 #DAW #JOPELINES #MicaEl6DelEquipo #Twitter #H2-console", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet11);
        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet12 = new Tweet("I am using Twitter; the best app", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet12);
        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet13 = new Tweet("I am using Twitter; the best app #RCLS", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet13);
        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet14 = new Tweet("I am using Twitter; the best app #DAW", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet14);
        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet15 = new Tweet("I am using Twitter; the best app #DAW", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet15);
        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet16 = new Tweet("I am using Twitter; the best app", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet16);images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet17 = new Tweet("I am using Twitter; the best app", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet17);images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet18 = new Tweet("I am using Twitter; the best app", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet18);
        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet19 = new Tweet("I am using Twitter; the best app #MySQL", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet19);
        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet20 = new Tweet("I am using Twitter; the best app #MySQL", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet20);
        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet21 = new Tweet("I am using Twitter; the best app #MySQL", users.get(4), LocalDateTime.of(2023,3,1,0,00,00), images);
        tweetRepository.save(tweet21);




        //Comments
        images = new Blob[] {
                null,
                null,
                null,
                null
        };
        Tweet tweet8 = new Tweet("Tienes razón \uD83E\uDD75\uD83E\uDD75", users.get(2), LocalDateTime.of(2023,02,20,14,04,00), images);
        tweetRepository.save(tweet7);
        tweetRepository.save(tweet8);
        tweets = tweetRepository.findAll();
        tweets.get(tweets.size()-2).addComment(tweets.get(tweets.size()-1));
        tweets.get(tweets.size()-2).addComment(tweets.get(tweets.size()-3));
        tweetRepository.save(tweets.get(tweets.size()-2));

        //Hashtags

        Set<Tweet> tweetsSet = new HashSet<>();
        tweetsSet.add(tweet11);
        tweetsSet.add(tweet6);
        Hashtag trend3 = new Hashtag("KingsLeague", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet11);
        tweetsSet.add(tweet13);
        Hashtag trend1 = new Hashtag("RCLS", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet2);
        Hashtag trend2 = new Hashtag("MarvelSpiderManMilesMorales", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet11);
        Hashtag trend4 = new Hashtag("Tailwind", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet11);
        Hashtag trend5 = new Hashtag("Pokemon", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet11);
        Hashtag trend7 = new Hashtag("Grupo8", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet11);
        tweetsSet.add(tweet14);
        tweetsSet.add(tweet15);
        Hashtag trend6 = new Hashtag("DAW", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet11);
        Hashtag trend8 = new Hashtag("JOPELINES", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet11);
        Hashtag trend9 = new Hashtag("MicaEl6DelEquipo", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet11);
        Hashtag trend10 = new Hashtag("Twitter", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet11);
        Hashtag trend11 = new Hashtag("H2-console", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet19);
        tweetsSet.add(tweet20);
        tweetsSet.add(tweet21);
        Hashtag trend12 = new Hashtag("MySQL", tweetsSet);
        trendRepository.save(trend1);
        trendRepository.save(trend2);
        trendRepository.save(trend3);
        trendRepository.save(trend4);
        trendRepository.save(trend5);
        trendRepository.save(trend6);
        trendRepository.save(trend7);
        trendRepository.save(trend8);
        trendRepository.save(trend9);
        trendRepository.save(trend10);
        trendRepository.save(trend11);
        trendRepository.save(trend12);

        //Sample notifications
        Tweet tweet = tweets.get(5);
        User user = users.get(2);
        Notification notification = new Notification(tweet,user,tweet.getUser(),LocalDateTime.of(2022,06,14,17,0,0),"LIKE");
        notificationRepository.save(notification);

        tweet = tweets.get(8);
        user = users.get(3);
        notification = new Notification(tweet,tweet.getUser(),user,LocalDateTime.of(2023,02,20,14,3,0),"RETWEET");
        notificationRepository.save(notification);

        tweet = tweets.get(7);
        user = users.get(2);
        notification = new Notification(tweet,tweet.getUser(),user,LocalDateTime.of(2023,02,20,14,9,0),"MENTION");
        notificationRepository.save(notification);

        User user_notificated = users.get(1);
        user = users.get(3);
        notification = new Notification(null,user_notificated,user,LocalDateTime.of(2020,04,11,15,3,0),"FOLLOW");
        notificationRepository.save(notification);

        user_notificated = users.get(2);
        user = users.get(1);
        notification = new Notification(null,user_notificated,user,LocalDateTime.of(2020,04,11,15,3,0),"FOLLOW");
        notificationRepository.save(notification);

        user_notificated = users.get(3);
        user = users.get(0);
        notification = new Notification(null,user_notificated,user,LocalDateTime.of(2020,04,11,15,3,0),"FOLLOW");
        notificationRepository.save(notification);

        user_notificated = users.get(0);
        user = users.get(2);
        notification = new Notification(null,user_notificated,user,LocalDateTime.of(2020,04,11,15,3,0),"FOLLOW");
        notificationRepository.save(notification);

        //Bookmarks
        User userAux = userRepository.findById(testUser.getId()).get();
        userAux.getBookmarks().add(tweets.get(0));
        userRepository.save(userAux);

        //Interactions
        tweets.get(5).addLike(users.get(2));
        tweets.get(8).addRetweet(users.get(2));
        tweets.get(8).addRetweet(users.get(3));
        tweetRepository.save(tweets.get(5));
        tweetRepository.save(tweets.get(8));
    }
}
