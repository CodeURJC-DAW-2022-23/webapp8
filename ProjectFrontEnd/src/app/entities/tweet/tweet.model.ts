import { User } from "../user/user.model";

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


