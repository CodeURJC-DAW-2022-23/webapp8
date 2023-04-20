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

  userId: number;
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
  isNotBanned: boolean;
  isFollowed: boolean;

  constructor(private userService: UserService,
    loginService: LoginService,
    activatedRoute: ActivatedRoute) {

    activatedRoute.params.subscribe(params => {
      const id = params['id'];
      this.userService.getUser(id).subscribe(
        user => {
          this.userInformation = user;
          this.userId = user.user.id;
          this.nickname = this.userInformation.user.nickname;
          this.username = this.userInformation.user.username;
          this.biography = this.userInformation.user.biography;
          this.joinDate = this.userInformation.user.joinDate;
          this.followedNumber = this.userInformation.followedNumber;
          this.followersNumber = this.userInformation.followersNumber;
          this.tweets = this.userInformation.tweets;
          this.urlToProfilePic = "/api/" + this.userInformation.urlToProfilePic;
          this.urlToBannerPic = "/api/" + this.userInformation.urlToBannerPic;

          userService.getCurrentUser().subscribe(
            user => {
              this.isLogged = user != null;
              let currentUser = user;

              this.userService.getUser(this.username).subscribe(
                user => {
                  this.isYourProfile = currentUser.user.id === user.user.id;
                  this.isAdmin = currentUser.user.role === "ADMIN";
                  this.isNotBanned = !user.user.enable;


                  this.userService.getFollowedUser(currentUser.user.username, this.username).subscribe(
                    find => this.isFollowed = find,
                    error => console.log(error)
                  )
                },
                error => console.log(error)
              );
            }
          );
        }
      );
    });
  }

  banUser() {
    this.userService.banUser(this.userId).subscribe(
      error => console.log(error)
    )
  };

  toggleFollowUser() {
    this.userService.toggleFollow(this.userId).subscribe(
      error => console.log(error)
    );
    this.isFollowed = !this.isFollowed;

    if (this.isFollowed) {
      this.followersNumber += 1;
      return;
    }

    this.followersNumber -= 1;
  };

}
