import { Component, Input } from '@angular/core';

/**
 *  TO USE THIS COMPONENT:
 *
 *  <app-banner-image [bannerType]="bannerType" [bannerSrc]="bannerSrc"></app-banner-image>
 *
 *  Being bannerType the type of the banner, and the bannerSrc, the url to the image
 */
@Component({
  selector: 'blue-button',
  templateUrl: './blue-button.component.html',
  styleUrls: ['./blue-button.component.css']
})
export class BlueButtonComponent {
  @Input()
  text: string;
  @Input()
  href: string;
  @Input()
  isInput: boolean;
  
  blueButtonClassMap = {
    "INDEX": " px-5 py-3 rounded-full font-bold w-full",
    "LOGIN-REGISTER": " px-10 py-2 rounded-full font-bold",
    "TWEET-HOME": " py-4 rounded-full font-bold text-xl",
    "SHOW-MORE":" px-5 py-2 rounded-3xl font-semibold w-fit text-sm",
    "GO-TO-PROFILE": " px-3 py-2 rounded-full font-semibold text-sm scale-90 xlsm:scale-100",
    "PUBLISH-TWEET":" px-5 py-3 rounded-full font-bold w-full text-xl"
  };
  blue_button: string = "text-center transition cursor-pointer text-white-0 bg-primary hover:scale-110";

  loadBlueButton(type: string){
    this.blue_button.concat(this.blueButtonClassMap[type]);
  }
}
