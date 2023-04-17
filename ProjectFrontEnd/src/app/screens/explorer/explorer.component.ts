import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router'; // To route the page when needed
import { HashtagService } from "src/app/services/hashtag.service";
import { hashtagComponent } from "src/app/entities/hashtag/hashtag.component"; 
import { of, map } from "rxjs";
import { TweetService } from "src/app/services/tweet-service";
import { Hashtag } from "src/app/entities/hashtag/hashtag.model";

@Component({
    selector: 'app-explorer',
    templateUrl: './explorer.component.html',
    styleUrls: ['./explorer.component.css']
  })

export class explorer implements OnInit{

    hashtagList: Hashtag[] = [];

    constructor(private router:Router, private service: HashtagService, private tweetService:TweetService) {}

    ngOnInit(): void {
        this.service.getSomeTrends().subscribe(
        response => this.hashtagList = response,
        error => this.hashtagList = []
          )
      }

      search(keyword:string){
        this.router.navigate(['/search/'+keyword])
      }
}
