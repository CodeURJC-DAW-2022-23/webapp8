import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router'; // To route the page when needed
import { HashtagService } from "src/app/servicies/hashtag-service";
import { Observable, of } from "rxjs";
import { Hahstag } from "./hashtag.interface";
import { hashtagComponent } from "./hashtag.component";

@Component({
    selector: 'app-explorer',
    templateUrl: './explorer.component.html',
    styleUrls: ['./hashtag.component.css']
  })

export class explorer implements OnInit{
    
    hashtags: hashtagComponent[] = [];

    constructor(private router:Router, private service: HashtagService) {}

    ngOnInit(): void {
        this.service.getSomeTrends().subscribe(
            loadedHashtags => {
              this.hashtags = loadedHashtags;
              console.log(this.hashtags);
            },
            error => console.log(error)
          );
    }
}