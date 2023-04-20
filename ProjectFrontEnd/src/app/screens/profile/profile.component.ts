import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TweetInformation } from 'src/app/entities/tweet/tweet.model';
import { UserInformation } from 'src/app/entities/user/user.model';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

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

  constructor(private userService: UserService,
    loginService: LoginService,
    activatedRoute: ActivatedRoute) {
    this.username = activatedRoute.snapshot.params['id'];
    this.isLogged = loginService.isLogged();
    let currentUser = loginService.currentUser;

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

        this.isYourProfile = currentUser.user.id === user.user.id;
        this.isAdmin = currentUser.user.type === "ADMIN";
        this.isBanned = user.user.enable;
        this.isFollowed = false;
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
}
