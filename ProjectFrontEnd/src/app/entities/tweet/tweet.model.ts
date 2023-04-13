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

interface TweetInformation{
    id: number;
    user: Profile;
    publishDate:Date;
    text:string;
}

interface Profile{
    id:number;
    username:string;
    nickname:string;
    type:string;
    enable:boolean
}