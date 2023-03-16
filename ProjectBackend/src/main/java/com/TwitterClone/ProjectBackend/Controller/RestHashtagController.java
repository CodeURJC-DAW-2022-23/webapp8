package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.MustacheObjects.InformationManager;
import com.TwitterClone.ProjectBackend.Model.MustacheObjects.TweetInformation;
import com.TwitterClone.ProjectBackend.Model.Trend;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import com.TwitterClone.ProjectBackend.userManagement.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * This REST controller manages all the petitions relation with hashtags
 */
@RestController
@RequestMapping("/api/hashtags")
public class RestHashtagController {
    @Autowired
    private HashtagService hashtagService;
    @Autowired
    private InformationManager informationManager;

    /**
     * Get some trends
     * @param from
     * @param size
     */
    @Operation(summary = "Get some of the current trends")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trends obtained", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Trend.class))
            }),
            @ApiResponse(responseCode = "404", description = "Trends not found", content = @Content)
    })
    @GetMapping("/trends")
    public ResponseEntity<List<Trend>> getSomeTrends(@PathParam("from") int from,
                                                     @PathParam("size") int size){
        List<Trend> newTrends = this.hashtagService.getCurrentTrends(from, size);

        if (newTrends.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(newTrends, HttpStatus.OK);
    }

    /**
     * Get some tweets associated to a hashtag
     * @param from
     * @param size
     * @param hashtag
     * @param request
     * @return
     */
    @Operation(summary = "Get some tweets associated to a hashtag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tweets obtained", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TweetInformation.class))
            }),
            @ApiResponse(responseCode = "404", description = "Tweets not found", content = @Content)
    })
    @GetMapping("/explore/{hashtag}")
    public ResponseEntity<List<TweetInformation>> getSomeTweetsAssociatedToAHashtag(
                                                            @PathParam("from") int from,
                                                            @PathParam("size") int size,
                                                            @PathVariable String hashtag,
                                                            HttpServletRequest request) {
        User currentUser = this.informationManager.getCurrentUser(request);
        List<Tweet> tweetsAssociated =
                this.hashtagService.getTweetsAssociatedTo(hashtag, from, size);
        List<TweetInformation> tweets =
                this.informationManager.calculateDataOfTweet(tweetsAssociated, currentUser);

        if (tweets.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }
}
