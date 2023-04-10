import { Component, Input } from '@angular/core';

/**
 *  TO USE THIS COMPONENT:
 *
 *  <app-nav-bar-button [text]="text" [selected]="selected"></app-nav-bar-button>
 *
 *  Being text the text shown in the button and selected if it is the tab selected
 */
@Component({
  selector: 'app-nav-bar-button',
  templateUrl: './nav-bar-button.component.html',
  styleUrls: ['./nav-bar-button.component.css']
})
export class NavBarButtonComponent {
  @Input()
  selected:boolean;

  @Input()
  text:string;

  navBarClassMap = {
    "selected":" font-bold border-primary dark:text-white-0 text-black-0",
    "unselected":" font-semibold border-transparent border-p-2 dark:text-gray-5 text-gray-4"
  }
  navBarClass: string = "py-4 border-b-4 cursor-pointer dark:hover:bg-gray-1 hover:bg-gray-3";
  
  ngOnInit(){
    if (this.selected){
      this.navBarClass += this.navBarClassMap["selected"];
    }else{
      this.navBarClass += this.navBarClassMap["unselected"];
    }
  }
}
