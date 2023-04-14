import { Component, Input } from '@angular/core';
import { Tweet } from './tweet.model';
import { ActivatedRoute, Router } from '@angular/router';
import { TweetService } from 'src/app/services/tweet-service';
import { waitForAsync } from '@angular/core/testing';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css']
})
export class TweetComponent {
  @Input()
  tweet:Tweet;
  @Input()
  isLogged:boolean;
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
  retweetClass:string = "w-8 p-[0.375rem] transition rounded-full group-hover:fill-green-0 group-hover:bg-green-2 fill-";
  likedClass:string = "w-8 p-[0.375rem] transition group-hover:bg-red-2 group-hover:fill-red-0 rounded-full fill-"
  bookmarkClass:string = "w-8 p-[0.375rem] transition rounded-full group-hover:bg-blue-1 group-hover:fill-primary fill-";

  goToTweet(){
    window.location.href = "/tweet/" + this.tweet.tweet.id
  }


  ngOnInit(): void{
    this.urlToComment = "/write-tweet/comment/" + this.tweet.tweet.id;
    this.profileIdURL = "/api/profile/" + this.tweet.tweet.user.id;
    this.tweet.urlToProfilePic = "/api/" + this.tweet.urlToProfilePic
    if (this.tweet.retweeted){
      this.retweetClass += "green-0";
    }else{
      this.retweetClass += "gray-4";
    }
    if (this.tweet.liked){
      this.likedClass += "red-0";
    }else{
      this.likedClass += "gray-4";
    }
    if (this.tweet.bookmarked){
      this.bookmarkClass += "primary";
    }else{
      this.bookmarkClass += "gray-4";
    }

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
  }
  giveLike(){
    if (! this.isLogged){
      return
    }
  }
  giveBookmark(){
    if (! this.isLogged){
      return
    }
  }

  deleteElement() {

  }
}
