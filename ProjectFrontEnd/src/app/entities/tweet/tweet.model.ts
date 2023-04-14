export interface Tweet{
    numComments:number;
    numLikes:number;
    numRetweets:number;
    retweeted:boolean;
    liked:boolean;
    bookmarked:boolean;
    urlToProfilePic:string;
    urlToMedia1:string;
    urlToMedia2:string;
    urlToMedia3:string;
    urlToMedia4:string;
    isAuthorised:boolean;
    tweet:TweetInformation;
    authorised:boolean;
}

export interface TweetInformation{
    id: number;
    user: User;
    publishDate:Date;
    text:string;
}

interface User{
    id:number;
    username:string;
    nickname:string;
    type:string;
    enable:boolean;
    biography: string;
    joinDate: string;
}

export interface UserInformation{
  user: User;
  urlToProfilePic: string;
  urlToBannerPic: string;
  followersNumber: number;
  followedNumber: number;
  tweets: Array<TweetInformation>;
}
