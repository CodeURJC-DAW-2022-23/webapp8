import { Component, Input } from '@angular/core';

/**
 *  TO USE THIS COMPONENT:
 *
 *  <app-floating-input [text]="text""></app-floating-input>
 *
 *  Being text the purpose of the input
 */
@Component({
  selector: 'app-floating-input',
  templateUrl: './floating-input.component.html',
  styleUrls: ['./floating-input.component.css']
})
export class FloatingInputComponent {
  @Input()
  text:string;
  
  inputClass = "block px-4 pb-2.5 pt-4 w-full text-gray-5 bg-transparent rounded-xl border-[1.9px] border-gray-15 dark:text-white-0 dark:border-gray-5 dark:focus:border-primary focus:outline-none focus:ring-0 focus:border-primary peer dark:valid:border-primary valid:outline-none valid:ring-0 valid:border-primary";
  labelClass = "absolute cursor-text text-gray-2 duration-200 transform -translate-y-4 scale-90 top-2 z-10 origin-[0] bg-white-0 dark:bg-black-0 px-2 peer-focus:px-2 peer-focus:text-primary peer-focus:dark:text-primary peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-90 peer-focus:-translate-y-4 left-2 peer-valid:px-2 peer-valid:text-primary peer-valid:dark:text-primary peer-valid:scale-90 peer-valid:-translate-y-4";
  divClass = "relative";
}
