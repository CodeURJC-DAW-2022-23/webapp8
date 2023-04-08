import { Component, Input } from '@angular/core';


/**
 *  TO USE THIS COMPONENT:
 *
 *  <app-profile-button [username]="username" [nickname]="nickname"></app-profile-button>
 *
 *  Being the username and the nickname from a user
 */
@Component({
  selector: 'app-profile-button',
  templateUrl: './profile-button.component.html',
  styleUrls: ['./profile-button.component.css']
})
export class ProfileButtonComponent {

  @Input()
  username: string;
  @Input()
  nickname: string;

  typeImg = 'PHOTO-NOTIFICATION';
  srcImg = "../../assets/profile0.jpg";

}
