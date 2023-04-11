import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css']
})
export class TweetComponent {
  @Input()
  id: number;
  @Input()
  profileId:number;
  @Input()
  urlToProfilePic:string;
  @Input()
  nick:string;
  @Input()
  username:string;
  @Input()
  publishDate:Date;
  @Input()
  text:string;
  @Input()
  numComments:number;
  @Input()
  numLikes:number;
  @Input()
  numRetweets:number;
  @Input()
  isRetweeted:boolean;
  @Input()
  isLiked:boolean;
  @Input()
  isBookmarked:boolean;
  @Input()
  isLogged:boolean;
  @Input()
  urlToMedia1:string;
  @Input()
  urlToMedia2:string;
  @Input()
  urlToMedia3:string;
  @Input()
  urlToMedia4:string;
  @Input()
  isAuthorised:boolean;

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
    window.location.href = "/tweet/" + this.id
  }

  ngOnInit(){
    this.urlToComment = "/write-tweet/comment/" + this.id;
    this.profileIdURL = "/profile/" + this.profileId;

    if (this.isRetweeted){
      this.retweetClass += "green-0";
    }else{
      this.retweetClass += "gray-4";
    }
    if (this.isLiked){
      this.likedClass += "red-0";
    }else{
      this.likedClass += "gray-4";
    }
    if (this.isBookmarked){
      this.bookmarkClass += "primary";
    }else{
      this.bookmarkClass += "gray-4";
    }

    this.isMedia1 = !(this.urlToMedia1 === "");
    this.isMedia2 = !(this.urlToMedia2 === "");
    this.isMedia3 = !(this.urlToMedia3 === "");
    this.isMedia4 = !(this.urlToMedia4 === "");
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
