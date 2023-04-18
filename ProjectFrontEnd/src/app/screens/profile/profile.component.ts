import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TweetInformation } from 'src/app/entities/tweet/tweet.model';
import { UserInformation } from 'src/app/entities/user/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  constructor(private userService: UserService,
    activatedRoute: ActivatedRoute) {
    this.username= activatedRoute.snapshot.params['id'];

    this.userService.getUser(this.username).subscribe(
      user => {
        this.userInformation = user;
        this.nickname = this.userInformation.user.nickname;
        this.username = this.userInformation.user.username;
        this.biography = this.userInformation.user.biography;
        this.joinDate = this.userInformation.user.joinDate;
        this.followedNumber = this.userInformation.followedNumber;
        this.followersNumber = this.userInformation.followersNumber;
        this.tweets = this.userInformation.tweets;
        this.urlToProfilePic = "/api/" + this.userInformation.urlToProfilePic;
        this.urlToBannerPic = "/api/" + this.userInformation.urlToBannerPic;
      },
      error => console.log(error)
    );

    this.userService.getCurrentUser().subscribe(
      user => {
        this.isLogged = user === null;
        this.isAdmin = user.user.type === "ADMIN";
      },
      error => console.log(error)
    );
  }

  ngOnInit() {
    console.log()
  }

  userId: string;
  userInformation: UserInformation;
  nickname: string;
  username: string;
  biography: string;
  joinDate: string;
  followedNumber: number;
  followersNumber: number;
  urlToProfilePic: string;
  urlToBannerPic: string;
  tweets: TweetInformation[];

  isLogged: boolean;
  isYourProfile: boolean;
  isAdmin: boolean;
  isBanned: boolean;
  isFollowed: boolean;



}
