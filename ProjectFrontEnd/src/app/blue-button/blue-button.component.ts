import { Component, Input } from '@angular/core';

/**
 *  TO USE THIS COMPONENT:
 *
 *  <app-blue-button [blueButtonType]="blueButtonType" [href]="href" [isInput]="isInput"></app-blue-button>
 *
 *  Being blueButtonType the type of the blueButton, href the page to go when button is clicked and isInput for the page we are
 */
@Component({
  selector: 'app-blue-button',
  templateUrl: './blue-button.component.html',
  styleUrls: ['./blue-button.component.css']
})
export class BlueButtonComponent {
  @Input()
  href: string;
  @Input()
  isInput: boolean;
  @Input()
  blueButtonType: string;

  text: string = "BLUE-BUTTON";

  blueButtonClassMap = {
    "INDEX": " px-5 py-3 rounded-full font-bold w-full",
    "LOGIN": " px-10 py-2 rounded-full font-bold",
    "REGISTER": " px-10 py-2 rounded-full font-bold",
    "TWEET-HOME": " py-4 rounded-full font-bold text-xl",
    "SHOW-MORE":" px-5 py-2 rounded-3xl font-semibold w-fit text-sm",
    "GO-TO-PROFILE": " px-3 py-2 rounded-full font-semibold text-sm scale-90 xlsm:scale-100",
    "PUBLISH-TWEET":" px-5 py-3 rounded-full font-bold w-full text-xl",
    "GO-TO-DASHBOARD":" px-4 py-2 rounded-full font-semibold scale-90 xlsm:scale-100 xlsm:hover:scale-110",
    "VERIFY":" px-3 py-2 rounded-full font-medium text-sm",
    "UNFOLLOW": "px-4 py-2 rounded-full font-bold"
  };

  blue_button: string = "text-center transition cursor-pointer text-white-0 bg-primary hover:scale-110";

  blueButtonTextMap = {
    "INDEX": "Sing up",
    "LOGIN": "Register",
    "REGISTER": "Login",
    "TWEET-HOME": "Tweet",
    "SHOW-MORE":"Show More",
    "GO-TO-PROFILE":"Go To Profile",
    "PUBLISH-TWEET":"Tweet",
    "GO-TO-DASHBOARD":"Go To Dashboard",
    "VERIFY":"Verify",
    "UNFOLLOW":"Unfollow"
  };

  goTo() {
    window.location.href = this.href
  }

  ngOnInit(){
    this.blue_button += this.blueButtonClassMap[this.blueButtonType];
    this.text = this.blueButtonTextMap[this.blueButtonType];
    this.isInput = ! this.isInput;
  }
}
