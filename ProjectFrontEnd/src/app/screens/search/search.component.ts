import { Component, OnInit,  Output, EventEmitter } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router'; // To route the page when needed
import { HashtagService } from "src/app/services/hashtag.service";
import { hashtagComponent } from "src/app/entities/hashtag/hashtag.component"; 
import { of, map } from "rxjs";
import { TweetService } from "src/app/services/tweet-service";
import { UserInformation } from "src/app/entities/user/user.model";
import SearchService from "src/app/services/search.service";
import { Hashtag } from "src/app/entities/hashtag/hashtag.model";

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.css']
  })

export class Search implements OnInit{

    hashtagList: Hashtag[] = [];
    userInformationList: UserInformation[] = [];
    keyword:string;
    showProfiles:boolean;

    constructor(private router:Router,activatedRoute: ActivatedRoute, private service: SearchService, private tweetService:TweetService) {       
        this.keyword = activatedRoute.snapshot.params['keyword'];
        this.showProfiles = true;
    }
    
    ngOnInit(): void {
        this.getAndLoadData(this.keyword);       
    }

    getAndLoadData(keyword:string){
        
        this.service.searchProfiles(keyword).subscribe(
            profileList => this.userInformationList = profileList,
            error => this.userInformationList = []
        )
        this.service.searchHashtags(keyword).subscribe(
            hashtagList => this.hashtagList = hashtagList,
            error => this.hashtagList = []
        )

        //Show results part!
    }

    setShowProfiles(show){
        if (show === "tweet"){           
            this.showProfiles = false;
            }else{
              this.showProfiles = true;
            }
    }

}