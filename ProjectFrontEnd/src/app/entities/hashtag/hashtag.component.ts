import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router';


import { HashtagService } from "src/app/servicies/hashtag-service";
import { Hahstag } from "./hashtag.interface";

@Component({
    selector: 'app-hashtagComponent',
    templateUrl: './hashtag.component.html',
    styleUrls: ['./hashtag.component.css']
  })

  export class hashtagComponent implements OnInit{
    
    @Input()
    hashtag: Hahstag;
    
    totalNumberOfTweets: number;

    constructor( private service: HashtagService, private router:Router) {}
    
    ngOnInit(): void {
        this.totalNumberOfTweets = this.hashtag.tweets.length;
    }

    showTweetsAssociated(){
    }
    
  }