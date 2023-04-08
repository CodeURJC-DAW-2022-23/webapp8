import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ProjectFrontEnd';

  username = "antonioalanxs";
  nickname = "Alanis"

  svgType = "LIKE-ICON";
  svgId = "svg"

  bannerType = "EDIT-BANNER"
  bannerSrc = "../assets/600x200.jpeg"
}
