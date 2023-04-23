import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router'; // To route the page when needed
import { HashtagService } from "src/app/services/hashtag.service";
import { TrendComponent } from "src/app/entities/trend/trend.component";
import { TweetService } from "src/app/services/tweet.service";
import { Trend } from "src/app/entities/trend/trend.model";

@Component({
    selector: 'app-explorer',
    templateUrl: './explorer.component.html',
    styleUrls: ['./explorer.component.css']
  })

export class ExplorerComponent implements OnInit{

    showTrends: boolean;
    hashtag: string;


    constructor(private router:Router) {}

    ngOnInit(): void {
        this.showTrends = true;
      }

    showTweets(hashtag){
      this.hashtag = hashtag;
      this.showTrends = false;
    }
}
