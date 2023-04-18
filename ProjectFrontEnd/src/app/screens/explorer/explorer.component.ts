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
    offset:number = 0;
    size:number = 10;
    

    constructor(private router:Router, private service: HashtagService, private tweetService:TweetService) {}

    ngOnInit(): void {
        this.getTrends();
      }

      getTrends() {
        this.service.getSomeTrends(this.offset, this.size).subscribe(
          response => response.forEach(h => this.hashtagList.push(h)),
          error => this.hashtagList = []
        );
      }  

      search(keyword:string){
        this.router.navigate(['/search/'+keyword])
      }

      loadMoreTrends() {
        this.addSpinner();
        this.offset += 10;
        this.getTrends();
        this.removeSpinner();
      }
  
      addSpinner() {
        document.getElementById("spinner").innerHTML = `<div class="flex items-center justify-center sticky">
                    <div class="inline-block h-8 w-8 animate-spin rounded-full border-4 border-primary border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]" role="status">
                        <span class="!absolute !-m-px !h-px !w-px !overflow-hidden !whitespace-nowrap !border-0 !p-0 ![clip:rect(0,0,0,0)]">
                            Loading...
                        </span>
                    </div>
                </div>`
      }
    
      removeSpinner() {
        document.getElementById("spinner").innerHTML = ``
      }
}
