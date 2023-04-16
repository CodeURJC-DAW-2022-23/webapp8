import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router';


import { HashtagService } from "src/app/services/hashtag.service";
import { Tweet, TweetInformation } from "../tweet/tweet.model";
import { TweetService } from "src/app/services/tweet-service";
import { TweetComponent } from "../tweet/tweet.component";
import { Hashtag } from "./hashtag.model";

@Component({
    selector: 'app-hashtagComponent',
    templateUrl: './hashtag.component.html',
    styleUrls: ['./hashtag.component.css'],
  })

  export class hashtagComponent implements OnInit{

    @Input()
    hashtagInfo: Hashtag;

    @Input()
    tweets : TweetInformation[];

    constructor( private service: HashtagService, private router:Router, private tweetService:TweetService) {}

    ngOnInit(): void {
        //this.totalNumberOfTweets = this.tweets.length;
    }

    showTweetsAssociated(){
      this.service.getTweetsAssociatedToAHashtag(this.hashtagInfo.hashtag).subscribe(
        tweet => this.tweets = tweet,
        error => console.log(error) // Change to redirectioning to the error screen!  
    )}

  }
