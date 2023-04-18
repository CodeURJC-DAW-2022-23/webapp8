import { Component, Input, OnInit, Output, EventEmitter } from "@angular/core";
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
    hashtags: Hashtag[];
    tweets : TweetInformation[];
    hashtagName: string;
    show: String = "hashtag"
    @Output() 
    whatToShow = new EventEmitter<String>();
    

    constructor( private service: HashtagService, private router:Router, private tweetService:TweetService) {}

    ngOnInit(): void {
        //this.totalNumberOfTweets = this.tweets.length;
    }

    showTweetsAssociated(hashtag: string){
      this.show = "tweet";
      this.hashtagName = hashtag;
      this.service.getTweetsAssociatedToAHashtag(hashtag).subscribe(
        tweet => this.tweets = tweet,
        error => this.router.navigate(['/error']) 
    )}

    sendShowHashtag(){
      this.whatToShow.emit(this.show);
    }    

  }
