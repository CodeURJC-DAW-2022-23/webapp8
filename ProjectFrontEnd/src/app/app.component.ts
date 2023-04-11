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

  text4 = "Mentions"

  selected1 = true
  selected2 = false

  searcherAction = "/search"
  searcherPlaceholder = "Search on Twitter"

  id = 1
  profileId = 1;
  urlToProfilePic = ""
  nick = "user"
  username = "user1"
  date = new Date(2023, 4, 21, 0, 0, 0, 0)
  textT = "tweet works!"
  numC = 0
  numL = 1
  numR = 2
  isR = true
  isL = true
  isB = false
  isIn = true
  u1 = ""
  u2 = ""
  u3 = ""
  u4 = ""
  authorised = true
}
