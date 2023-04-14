import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router';


import { HashtagService } from "src/app/services/hashtag-service";
import { Tweet } from "../tweet/tweet.model";
import { TweetService } from "src/app/services/tweet-service";
import { TweetComponent } from "../tweet/tweet.component";

@Component({
    selector: 'app-hashtagComponent',
    templateUrl: './hashtag.component.html',
    styleUrls: ['./hashtag.component.css'],
  })

  export class hashtagComponent implements OnInit{

    @Input()
    hashtag: string = "";

    @Input()
    numTweets: number = 0;

    @Input()
    tweets: TweetComponent[] = [];

    constructor( private service: HashtagService, private router:Router, private tweetService:TweetService) {}

    ngOnInit(): void {
        //this.totalNumberOfTweets = this.tweets.length;
    }

    showTweetsAssociated(){
      this.service.getTweetsAssociatedToAHashtag(this.hashtag).subscribe(
        (response => {
          this.tweets = response.map((obj:TweetComponent) => {
            let tweetComp = new TweetComponent(this.router,this.tweetService);
            tweetComp = obj;
            console.log(tweetComp); // Temporal, just for local testing
            return tweetComp;
          })
        }
      )
    )}

  }
