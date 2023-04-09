import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ProjectFrontEnd';

  type = "LEFT-BAR"
  src = "#nada"

  blueButtonType = "VERIFY"
  isInput = false

  text = "Ban user"

  text2 = "Cancel"

  text3 = "Username"
}
