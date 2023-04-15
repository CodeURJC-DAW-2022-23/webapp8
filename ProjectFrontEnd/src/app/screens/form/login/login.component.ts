import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router'; // To route the page when needed
import { HashtagService } from "src/app/services/hashtag.service";
import { hashtagComponent } from "src/app/entities/hashtag/hashtag.component"; 
import { of, map } from "rxjs";
import { TweetService } from "src/app/services/tweet-service";
import { LoginService } from "src/app/services/login.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
  })

export class LoginComponent{


    constructor(private router:Router, private service: LoginService) {}

    login(event: any, username:string, password: string){
        event.preventDefault();

        this.service.logIn(username,password);
    }

    
}