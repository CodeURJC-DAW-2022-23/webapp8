import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router'; // To route the page when needed
import { HashtagService } from "src/app/services/hashtag.service";
import { hashtagComponent } from "src/app/entities/hashtag/hashtag.component"; 
import { of, map } from "rxjs";
import { TweetService } from "src/app/services/tweet-service";

@Component({
    selector: 'app-explorer',
    templateUrl: './explorer.component.html',
    styleUrls: ['./explorer.component.css']
  })

export class explorer implements OnInit{

    hashtagComponentsList: hashtagComponent[] = [];

    constructor(private router:Router, private service: HashtagService, private tweetService:TweetService) {}

    ngOnInit(): void {
        this.service.getSomeTrends().subscribe(
          (response: any[]) => {
            this.hashtagComponentsList = response.map((obj: any) => {
              const hashtagComp = new hashtagComponent(this.service, this.router, this.tweetService);
              hashtagComp.hashtagInfo = obj;
              return hashtagComp;
            });
          },
          error => console.log(error)
        );

      }
}
