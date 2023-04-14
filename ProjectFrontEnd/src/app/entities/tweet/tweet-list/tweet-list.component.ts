import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TweetService } from 'src/app/services/tweet-service';
import { Tweet } from '../tweet.model';

@Component({
  selector: 'app-tweet-list',
  templateUrl: './tweet-list.component.html',
  styleUrls: ['./tweet-list.component.css']
})
export class TweetListComponent {
  isLogged:boolean;
  tweets: Tweet[];
  constructor(private router:Router, private service: TweetService) {}
  ngOnInit(){
    this.isLogged = true
    let id = 3
    this.service.getTweetsOfAUser(id, 0, 10).subscribe(
        tweet => this.tweets = tweet,
        error => console.error(error)
    );
  }
}
