import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User, UserInformation } from 'src/app/entities/user/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent {

  user: User;
  urlToBannerPic: string;
  urlToProfilePic: string;

  constructor(private userService: UserService,
    activatedRouter: ActivatedRoute,
    private router: Router,) {
    const userId = activatedRouter.snapshot.params['id'];
    this.userService.getUser(userId).subscribe(
      user => {
        this.user = user.user;
        this.urlToBannerPic = '/api/' + user.urlToBannerPic;
        this.urlToProfilePic = '/api/' + user.urlToProfilePic;
      }
    );
  }

  editProfile(event: any, banner: any, profile: any, nickname: string, biography: string) {
    event.preventDefault();

    if (banner.length !== 0) {
      let file = banner[0];

      if (this.isValidFile(file)) {
        this.userService.putBannerPic(file, this.user.id).subscribe();
      }
    }

    if (profile.length !== 0) {
      let file = profile[0];

      if (this.isValidFile(file)) {
        this.userService.putProfilePic(file, this.user.id).subscribe();
      }
    }

    this.userService.putNickname(nickname, this.user.id).subscribe(
      response => {
        this.userService.putBiography(biography, this.user.id).subscribe(
          response => {
            this.router.navigate(['/profile', this.user.username])
          }
        )
      }

    );
  }

  private isValidFile(element: any): boolean {
    let fileExtension = element.name.slice((element.name.lastIndexOf(".") - 1 >>> 0) + 2);

    if (fileExtension === "gif" || fileExtension === "png" || fileExtension === "jpg" || fileExtension === "jpeg") {
      return true;
    };

    return false;
  }
}



