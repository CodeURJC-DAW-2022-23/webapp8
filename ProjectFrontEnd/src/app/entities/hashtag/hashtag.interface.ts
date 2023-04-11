import { TweetComponent } from "../tweet/tweet.component";

export interface Hashtag{
    hashtag: string;
    tweets: TweetComponent[]; 
}