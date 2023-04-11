import { Component, Input } from '@angular/core';

/**
 *  TO USE THIS COMPONENT:
 *
 *  <app-button-without-background-white-border-and-text [text]="text" [href]="href"></app-button-without-background-white-border-and-text>
 *
 *  Being text the text shown in the button and href the page to go when button is clicked
 */
@Component({
  selector: 'app-button-without-background-white-border-and-text',
  templateUrl: './button-without-background-white-border-and-text.component.html',
  styleUrls: ['./button-without-background-white-border-and-text.component.css']
})
export class ButtonWithoutBackgroundWhiteBorderAndTextComponent {
  @Input()
  href: string;
  @Input()
  text:string;

  buttonClass:string = " text-sm font-bold text-center transition bg-transparent border-2 cursor-pointer border-black-0 rounded-3xl dark:border-white-0 dark:text-white-0 text-black-0 hover:scale-110";

  buttonClassMap = {
    "Cancel": " px-5 py-3",
    "Edit profile": " px-4 py-2 w-fit",
    "Have you forgotten your password?": " px-10 py-2",
  };

  goTo() {
    window.location.href = this.href
  }

  ngOnInit(){
    this.buttonClass += this.buttonClassMap[this.text];
  }
}
