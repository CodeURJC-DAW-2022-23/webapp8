import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ProjectFrontEnd';
  typeImg = 'PHOTO-NOTIFICATION';
  srcImg = "../../assets/profile0.jpg";

  svgType = "LIKE-ICON";
  svgId = "svg"

  bannerType = "EDIT-BANNER"
  bannerSrc = "../assets/600x200.jpeg"
}
