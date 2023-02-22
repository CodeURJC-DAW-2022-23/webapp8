package Services;

import Model.Tweet;
import Model.User;
import Repository.TweetRepository;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DBInitializer {
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() throws IOException, URISyntaxException {

        //Sample Users
        String [] files = {"example_data/elrubius_profilepic.jpg","example_data/elrubius_profilebanner.jpg"};
        User user1 = new User("@Rubiu5","elrubius","Rata Noruega.  Me gustan los gatos obesos.","rubius@gmail.com","rubius",files, LocalDate.of(2013,10,13),"Verified");
        files = new String[]{"example_data/KOI_KEYLAND_profilepic.jpg", "example_data/KOI_KEYLAND_profilebanner.jpg"};
        User user2 = new User("@Keyland71", "KOI KEYLAND71", "19 y/o\nRocket League proplayer for @KOI", "example2@gmail.com", "examplePassword2",files, LocalDate.of(2018,4,21), "Registered");
        files = new String[]{"example_data/Alanis_profilepic.jpg", "example_data/Alanis_profilebanner.jpg"};
        User user3 = new User("@antonioalanxs", "Alanís",  "",  "example3@gmail.com", "examplePassword3",files, LocalDate.of(2019,8,7), "Privated");
        files = new String[]{"example_data/Ibai_profilepic.jpg", "example_data/Ibai_profilebanner.jpg"};
        User user4 = new User("@ibai","Ibai","Sigue a nuestros equipos @KOI y @PorcinosFC, http://twitch.tv/ibai","ibai@gmail.com","ibai",files, LocalDate.of(2014,8,5), "Verified");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        //Sample Tweets
        Tweet tweet1 = new Tweet("Fotito con mi fan \uD83D\uDCAA\uD83C\uDFFD\uD83D\uDCAA\uD83C\uDFFD\uD83D\uDD25\uD83D\uDD25", user2, LocalDateTime.of(2022,06,14,16,03,00), null);
        Tweet tweet2 = new Tweet("MarvelsSpiderManMilesMorales", user2, LocalDateTime.of(2021,8,16,20,00,00), null);
        Tweet tweet3 = new Tweet("Hoy he hecho autostop en un pueblo alejado de la mano de dios en Japón y me he montado con el perro mas majo del mundo 犬", user1, LocalDateTime.of(2023,02,14,20,30,05), null);
        Tweet tweet5 = new Tweet("Devastado, afligido, descorazonado, atormentado, apenado, entristecido, desolado, triste, cabizbajo, lloroso, cariacontecido, compungido, destruido, mustio, apesadumbrado, deshecho, demolido.", user4, LocalDateTime.of(2023,02,14,20,00,00), null);
        Tweet tweet4 = new Tweet("Deja de llorar", user1, LocalDateTime.of(2023,02,19,20,00,00), tweet5);
        Tweet tweet6 = new Tweet("Chup chup con la #KingsLeague", user4, LocalDateTime.of(2023,02,14,21,00,00), null);
        Tweet tweet7 = new Tweet("En primaria deberían poner una asignatura de coger aceitunas @Keyland71", user3, LocalDateTime.of(2023,02,20,14,03,00), null);
        Tweet tweet8 = new Tweet("Tienes razón \uD83E\uDD75\uD83E\uDD75", user3, LocalDateTime.of(2023,02,20,14,04,00), null);
        Tweet tweet9 = new Tweet("no titiritititiiiiii", user2, LocalDateTime.of(2023,02,20,14,9,00), null);
        Tweet tweet10 = new Tweet("Creéis que lo de Shakira iba por Piqué?", user4, LocalDateTime.of(2023,01,12,1,55,05), null);
        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);
        tweetRepository.save(tweet3);
        tweetRepository.save(tweet5);
        tweetRepository.save(tweet4);
        tweetRepository.save(tweet6);
        tweetRepository.save(tweet7);
        tweetRepository.save(tweet8);
        tweetRepository.save(tweet9);
        tweetRepository.save(tweet10);

    }
}
