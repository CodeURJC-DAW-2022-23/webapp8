package com.TwitterClone.ProjectBackend.Controller.RestController;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.TweetInformation;
import com.TwitterClone.ProjectBackend.Model.Notification;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.Service.NotificationService;
import com.TwitterClone.ProjectBackend.Service.ProfileService;
import com.TwitterClone.ProjectBackend.Service.TweetService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Negative;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class RestTweetController {
    @Autowired
    private TweetService tweetService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private InformationManager informationManager;
    @Autowired
    private NotificationService notificationService;

    interface Basic extends Tweet.Basic, TweetInformation.Basic, User.Basic {
    }

    ;

    @Operation(summary = "Get some of the tweets owned by the followed users of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tweets obtained", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TweetInformation.class))
            }),
            @ApiResponse(responseCode = "404", description = "No tweets found for current user", content = @Content)
    })
    @GetMapping("/tweets")
    @JsonView(Basic.class)
    public ResponseEntity<List<TweetInformation>> getTweetsForAUser(@PathParam("from") int from,
                                                                    @PathParam("size") int size,
                                                                    HttpServletRequest request) {
        User user = this.informationManager.getCurrentUser(request);
        List<Tweet> tweets = this.tweetService.findSomeRecentForUser(user.getId(), from, size);
        List<TweetInformation> tweetsInformation = this.informationManager.calculateDataOfTweet(tweets, user);

        if (tweetsInformation.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tweetsInformation, HttpStatus.OK);
    }

    @Operation(summary = "Get some of the bookmarks of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookmarks obtained", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TweetInformation.class))
            }),
            @ApiResponse(responseCode = "202", description = "Bookmarks list empty", content = @Content)
    })
    @GetMapping("/bookmarks")
    @JsonView(Basic.class)
    public ResponseEntity<List<TweetInformation>> getTweetsForBookmarks(@PathParam("from") int from,
                                                                        @PathParam("size") int size,
                                                                        HttpServletRequest request) {
        User user = this.informationManager.getCurrentUser(request);
        List<Tweet> tweets = this.profileService.getBookmarks(user.getId(), from, size);
        List<TweetInformation> tweetsInformation = this.informationManager.calculateDataOfTweet(tweets, user);

        if (tweetsInformation.size() == 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(tweetsInformation, HttpStatus.OK);
    }

    @Operation(summary = "Get some of the tweets written by the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tweets obtained", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TweetInformation.class))
            }),
            @ApiResponse(responseCode = "404", description = "Tweets not found", content = @Content),
            @ApiResponse(responseCode = "202", description = "User not found", content = @Content)
    })
    @GetMapping("/profile/{id}/tweets")
    @JsonView(Basic.class)
    public ResponseEntity<List<TweetInformation>> getProfileTweets(@PathVariable Long id,
                                                                   @PathParam("from") int from,
                                                                   @PathParam("size") int size,
                                                                   HttpServletRequest request) {
        if (this.profileService.findById(id).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        User user = this.informationManager.getCurrentUser(request);
        List<Tweet> tweets = this.tweetService.findSomeTweetOfUser(id, from, size);
        List<TweetInformation> tweetsInformation = this.informationManager.calculateDataOfTweet(tweets, user);

        if (tweetsInformation.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tweetsInformation, HttpStatus.OK);
    }

    //It makes an error in console but works
    @Operation(summary = "Post a new tweet by the current user replying a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Tweet.class))
            }),
            @ApiResponse(responseCode = "404", description = "Tweet Not Found", content = @Content)
    })
    @PostMapping("/tweets/reply-tweet/{idTweetReplied}")
    @JsonView(Basic.class)
    public ResponseEntity<Tweet> postTweet(@RequestParam("text") String tweet_info,
                                           @RequestParam("files") MultipartFile[] tweet_files,
                                           @PathVariable("idTweetReplied") Long idTweetReplied,
                                           HttpServletRequest request) throws IOException {
        Tweet tweetReplied = this.tweetService.findById(idTweetReplied).orElse(null);
        User currentUser = this.informationManager.getCurrentUser(request);
        if(currentUser == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (tweetReplied == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user_reply = tweetReplied.getUser();

        Blob[] files = this.informationManager.manageImages(tweet_files);
        Long userId = currentUser.getId();
        Tweet newTweet = this.tweetService.createTweet(tweet_info, files, userId);
        this.informationManager.processTextTweet(tweet_info, newTweet, currentUser);

        tweetService.addComment(idTweetReplied, newTweet);

        if (!userId.equals(user_reply.getId())) {
            this.notificationService
                    .createNotification(newTweet.getId(), user_reply, currentUser, "MENTION");
        }

        return new ResponseEntity<>(newTweet, HttpStatus.OK);
    }

    //It makes an error in console but works
    @Operation(summary = "Post a new tweet by the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tweet Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Tweet.class))
            })
    })
    @PostMapping("/tweets/new-tweet")
    @JsonView(Basic.class)
    public ResponseEntity<Tweet> postTweet(@RequestParam("text") String tweet_info,
                                           @RequestParam("files") MultipartFile[] tweet_files,
                                           HttpServletRequest request) throws IOException {
        Blob[] files = this.informationManager.manageImages(tweet_files);
        User currentUser = this.informationManager.getCurrentUser(request);
        if (currentUser == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Long userId = currentUser.getId();
        Tweet newTweet = this.tweetService.createTweet(tweet_info, files, userId);
        this.informationManager.processTextTweet(tweet_info, newTweet, currentUser);

        return new ResponseEntity<>(newTweet, HttpStatus.OK);
    }

    @Operation(summary = "Delete a tweet of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tweet Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Tweet.class))
            }),
            @ApiResponse(responseCode = "404", description = "Tweet Not Found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden action", content = @Content)
    })
    @DeleteMapping("/tweet/delete/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<Tweet> deleteTweet(@PathVariable("id") Long id,
                                             HttpServletRequest request) {
        Tweet tweet = this.tweetService.findById(id).orElse(null);

        if ((tweet != null)) {

            List<Tweet> tweets = new ArrayList<>();
            tweets.add(tweet);
            User currentUser = this.informationManager.getCurrentUser(request);
            List<TweetInformation> tweetInformation = this.informationManager.calculateDataOfTweet(tweets, currentUser);

            if (!tweetInformation.get(0).isAuthorised()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            this.tweetService.deleteTweet(tweet);
            return new ResponseEntity<>(tweet, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Give or remove like of a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like given", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TweetInformation.class))
            }),
            @ApiResponse(responseCode = "202", description = "Like removed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TweetInformation.class))
            }),
            @ApiResponse(responseCode = "404", description = "Tweet Not Found", content = @Content),
    })
    @PutMapping("/tweet/like/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<TweetInformation> toggleLike(@PathVariable("id") Long id,
                                                       HttpServletRequest request) {
        Tweet tweet = this.tweetService.findById(id).orElse(null);

        if (tweet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User currentUser = this.informationManager.getCurrentUser(request);
        boolean hasLiked = tweetService.toggleLike(currentUser, tweet);

        List<Tweet> list = new ArrayList<>();
        list.add(tweet);
        List<TweetInformation> tweetInformation = this.informationManager.calculateDataOfTweet(list, currentUser);

        if (hasLiked) {
            return new ResponseEntity<>(tweetInformation.get(0), HttpStatus.OK);
        }

        return new ResponseEntity<>(tweetInformation.get(0), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Give or remove retweet of a tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retweet given", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TweetInformation.class))
            }),
            @ApiResponse(responseCode = "202", description = "Retweet removed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TweetInformation.class))
            }),
            @ApiResponse(responseCode = "404", description = "Tweet Not Found", content = @Content)
    })
    @PutMapping("/tweet/retweet/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<TweetInformation> toggleRetweet(@PathVariable("id") Long id,
                                                          HttpServletRequest request) {
        Tweet tweet = this.tweetService.findById(id).orElse(null);

        if (tweet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User currentUser = this.informationManager.getCurrentUser(request);
        boolean hasRetweeted = this.tweetService.toggleRetweet(currentUser, tweet);

        List<Tweet> list = new ArrayList();
        list.add(tweet);
        List<TweetInformation> tweetInformation = this.informationManager.calculateDataOfTweet(list, currentUser);

        if (hasRetweeted) {
            return new ResponseEntity<>(tweetInformation.get(0), HttpStatus.OK);
        }

        return new ResponseEntity<>(tweetInformation.get(0), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Add or remove a tweet from the bookmark list of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookmarked", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TweetInformation.class))
            }),
            @ApiResponse(responseCode = "202", description = "Bookmark Removed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TweetInformation.class))
            }),
            @ApiResponse(responseCode = "404", description = "Tweet Not Found", content = @Content)
    })
    @PutMapping("/tweet/bookmark/{id}")
    @JsonView(Basic.class)
    public ResponseEntity<TweetInformation> toggleBookmark(@PathVariable("id") Long id,
                                                           HttpServletRequest request) {
        Tweet tweet = this.tweetService.findById(id).orElse(null);

        if (tweet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User currentUser = this.informationManager.getCurrentUser(request);
        boolean hasBookmarked = this.tweetService.toggleBookmark(currentUser, tweet);

        List<Tweet> bookmarks = new ArrayList<>();
        bookmarks.add(tweet);
        List<TweetInformation> tweetInformation = this.informationManager.calculateDataOfTweet(bookmarks, currentUser);

        if (hasBookmarked) {
            return new ResponseEntity<>(tweetInformation.get(0), HttpStatus.OK);
        }

        return new ResponseEntity<>(tweetInformation.get(0), HttpStatus.ACCEPTED);
    }
}
