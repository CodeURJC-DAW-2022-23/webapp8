import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TweetInformation } from './tweet.model';
import { Router } from '@angular/router';
import { TweetService } from 'src/app/services/tweet-service';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css']
})
export class TweetComponent {
  @Input()
  tweet:TweetInformation;
  @Input()
  isLogged:boolean;
  @Output()
  delete = new EventEmitter<boolean>();
  constructor(private router: Router, private service: TweetService) {
}

  urlToComment:string;
  profileIdURL:string;

  isMedia1:boolean;
  isMedia2:boolean;
  isMedia3:boolean;
  isMedia4:boolean;
  threeImages: boolean;

  imageClass:string = "max-h-[500px] border bg-gray-4 dark:bg-twitter-dark-gray border-gray-4 rounded-xl overflow-hidden";

  ngOnInit(): void{
    this.tweet.urlToProfilePic = "/api/" + this.tweet.urlToProfilePic

    this.isMedia1 = !(this.tweet.urlToMedia1 === "");
    this.isMedia2 = !(this.tweet.urlToMedia2 === "");
    this.isMedia3 = !(this.tweet.urlToMedia3 === "");
    this.isMedia4 = !(this.tweet.urlToMedia4 === "");
    this.tweet.urlToMedia1 = "/api/" + this.tweet.urlToMedia1
    this.tweet.urlToMedia2 = "/api/" + this.tweet.urlToMedia2
    this.tweet.urlToMedia3 = "/api/" + this.tweet.urlToMedia3
    this.tweet.urlToMedia4 = "/api/" + this.tweet.urlToMedia4
    this.threeImages = this.isMedia3 && !this.isMedia4;
    if (this.isMedia2){
      this.imageClass += "grid grid-cols-2";
    }
    if (this.isMedia4){
      this.imageClass += "grid grid-rows-2";
    }
  }
  giveRetweet(){
    if (! this.isLogged){
      return
    }
    this.service.toggleRetweet(this.tweet.tweet.id).subscribe(
      tweet => {
        this.tweet.retweeted = tweet.retweeted;
        this.tweet.numRetweets = tweet.numRetweets;
      },
      error => console.error(error)
    );

  }
  giveLike(){
    if (! this.isLogged){
      return
    }
    this.service.toggleLike(this.tweet.tweet.id).subscribe(
      tweet => {
        this.tweet.liked = tweet.liked;
        this.tweet.numLikes = tweet.numLikes;
      },
      error => console.error(error)
    );
  }
  giveBookmark(){
    if (! this.isLogged){
      return
    }
    this.service.toggleBookmark(this.tweet.tweet.id).subscribe(
      tweet => {
        this.tweet.bookmarked = tweet.bookmarked;
      },
      error => console.error(error)
    );
  }

  deleteElement() {
    let okResponse = window.confirm("Do you want to remove this tweet?");
        if (okResponse) {
          this.service.deleteTweet(this.tweet.tweet.id).subscribe(
                tweet => this.delete.emit(true),
                error => console.error(error)
            )
        }
  }
}
