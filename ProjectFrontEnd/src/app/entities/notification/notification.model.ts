import { Tweet } from "../tweet/tweet.model";
import { UserInformation } from "../user/user.model";

export interface Notification {

    id: number;
    tweetTrigger: Tweet;
    userWhoNotifies: UserInformation;
    notificationType: string;
}
