import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/entities/user/user.model';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-left-bar',
  templateUrl: './left-bar.component.html',
  styleUrls: ['./left-bar.component.css']
})
export class LeftBarComponent {

  nickname: String;
  username: String;
  urlToProfilePicture: String;

  constructor(private router: Router, private userService: UserService, private loginService: LoginService) {
    this.userService.getCurrentUser().subscribe(userInformation => {
      this.urlToProfilePicture = "/api/" + userInformation.urlToProfilePic

      let user: User;
      user = userInformation.user;

      this.nickname = user.nickname;
      this.username = user.username;
    });
  }

  logOut() {
    this.loginService.logOut();
  }
}
