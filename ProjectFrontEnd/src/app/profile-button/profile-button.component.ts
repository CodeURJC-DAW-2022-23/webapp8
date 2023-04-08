import { Component, Input } from '@angular/core';

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
