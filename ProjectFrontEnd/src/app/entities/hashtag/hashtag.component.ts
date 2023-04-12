import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router';


import { HashtagService } from "src/app/servicies/hashtag-service";
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

    constructor( private service: HashtagService, private router:Router) {}
    
    ngOnInit(): void {
        //this.totalNumberOfTweets = this.tweets.length;
    }

    showTweetsAssociated(){
    }
    
  }