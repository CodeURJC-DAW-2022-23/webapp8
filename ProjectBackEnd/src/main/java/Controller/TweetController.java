package Controller;

import Model.Tweet;
import Services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("tweets/")
@RestController
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @GetMapping
    public List<Tweet> getAllTweets() {
        return tweetService.findAll();
    }

    @GetMapping(path = "{id}")
    public Tweet getOneTweet(@PathVariable("id") UUID id) {
        return tweetService.findById(id).orElse(null);
    }

    @PostMapping
    public void postTweet(@RequestBody String text){
        tweetService.createTweet(text);
    }
}