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

  svgType = "BRAND-ICON";
  svgId = "svg"

  bannerType = "EDIT-BANNER";
  bannerSrc = "../assets/600x200.jpeg";

  typeElement = "TWEETS-OF-A-USER";
  userId = 5;
  hashtagId = "DAW";

  typeButton = "SEE-TWITTER"

  text = "Next"
  src = "#nada"
}
