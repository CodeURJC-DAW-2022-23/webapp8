import { Component, Input } from '@angular/core';
import { UserInformation } from '../tweet/tweet.model';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent{
  @Input()
  userInformation: UserInformation;
  @Input()
  isLeftBar: boolean;

  typeImg: string;
  srcImg: string;
  nickname: string;
  username: string;


  ngOnInit() {
    this.typeImg = "PHOTO-LEFT-BAR";
    this.srcImg = this.userInformation.urlToProfilePic;
    this.nickname = this.userInformation.user.nickname;
    this.username = this.userInformation.user.username
  }

}
