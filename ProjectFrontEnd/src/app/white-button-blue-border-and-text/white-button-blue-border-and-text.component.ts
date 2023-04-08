import { Component, Input } from '@angular/core';

/**
 *  HOW TO USE THIS COMPONENT:
 *
 *  <app-white-button-blue-border-and-text [typeButton]="typeButton"></app-white-button-blue-border-and-text>
 *
 *  Being typeButton the type of white button with blue border and text
 */
@Component({
  selector: 'app-white-button-blue-border-and-text',
  templateUrl: './white-button-blue-border-and-text.component.html',
  styleUrls: ['./white-button-blue-border-and-text.component.css']
})
export class WhiteButtonBlueBorderAndTextComponent {
  @Input()
  typeButton: string;

  textButton: string;
  classButton: string;
  srcButton: string;
  textButtonMap = {
    "SEE-TWITTER": "Explore Twitter"
  }
  classButtonMap = {
    "SEE-TWITTER": "absolute flex items-center gap-2 py-3 pl-8 pr-5 transition scale-90 border-2 rounded-full cursor-pointer top-7 right-4 w-fit hover:scale-100 border-primary"
  }
  srcButtonMap = {
    "SEE-TWITTER": "./explore.html"
  }

  loadButton(type:string) {
    this.textButton = this.textButtonMap[type];
    this.classButton = this.classButtonMap[type];
    this.srcButton = this.srcButtonMap[type];
  }


}
