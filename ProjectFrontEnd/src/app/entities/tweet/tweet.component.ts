import { Component, Input } from '@angular/core';
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

  ngOnInit(): void{
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
    this.service.toggleRetweet(this.tweet.tweet.id).subscribe(
      tweet => this.tweet = tweet,
      error => console.error(error)
    );
    if (this.tweet.retweeted){
      this.retweetClass.replace("fill-gray-4", "fill-green-0");
    }else{
      this.retweetClass.replace("fill-green-0", "fill-gray-4");
    }

  }
  giveLike(){
    if (! this.isLogged){
      return
    }
    this.service.toggleLike(this.tweet.tweet.id).subscribe(
      tweet => this.tweet = tweet,
      error => console.error(error)
    );
    if (this.tweet.retweeted){
      this.retweetClass.replace("fill-gray-4", "fill-red-0");
    }else{
      this.retweetClass.replace("fill-red-0", "fill-gray-4");
    }
  }
  giveBookmark(){
    if (! this.isLogged){
      return
    }
    this.service.toggleBookmark(this.tweet.tweet.id).subscribe(
      tweet => this.tweet = tweet,
      error => console.error(error)
    );
    if (this.tweet.retweeted){
      this.retweetClass.replace("fill-gray-4", "fill-primary");
    }else{
      this.retweetClass.replace("fill-primary", "fill-gray-4");
    }
  }

  deleteElement() {
    this.service.deleteTweet(this.tweet.tweet.id);
    //TODO
  }
}
