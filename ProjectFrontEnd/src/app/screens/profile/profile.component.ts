import { Component, Input } from '@angular/core';
import { UserInformation } from 'src/app/entities/user/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  @Input()
  userId: string;

  constructor(private userService: UserService) {}

  userInformation: UserInformation;
  isAdmin: boolean;
  isYourProfile: boolean;



  ngOnInit() {
    this.userService.getUser(this.userId).subscribe(
       user => this.userInformation = user,
       error => console.log(error)
    )
  }
}
