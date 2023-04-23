import { Component } from '@angular/core';

import { UserService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';

import { User } from 'src/app/entities/user/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-left-bar',
  templateUrl: './left-bar.component.html',
  styleUrls: ['./left-bar.component.css'],
  providers: [UserService, LoginService]
})
export class LeftBarComponent {

  public nickname: string;
  public username: string;
  public urlToProfilePicture: string;
  isLogged: boolean;
  navigation: string = '';

  constructor(private _userService: UserService,
    private _loginService: LoginService,
    private router: Router) {
      this._userService.getCurrentUser().subscribe(userInformation => {

        this.isLogged = userInformation !== null;
        if(!this.isLogged){
          return
        }
        this.navigation = '/home'

        this.urlToProfilePicture = "/api/" + userInformation.urlToProfilePic

        let user: User;
        user = userInformation.user;

        this.nickname = user.nickname;
        this.username = user.username;
      });
  }

  logOut() {
    this._loginService.logOut();
    this.router.navigateByUrl("/")
  }
}
