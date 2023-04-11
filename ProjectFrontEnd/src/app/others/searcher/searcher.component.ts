import { Component, Input } from '@angular/core';

/**
 *  HOW TO USE THIS COMPONENT:
 *
 * <app-searcher [action]="/search" [placeholder]="Search on Twitter"></app-searcher>
 */
@Component({
  selector: 'app-searcher',
  templateUrl: './searcher.component.html',
  styleUrls: ['./searcher.component.css']
})
export class SearcherComponent {
  @Input()
  action:string;

  @Input()
  placeholder:string;

  formClass:string = "relative";
  inputClass:string = "w-full py-3 text-lg rounded-full pl-14 text-black-0 dark:text-gray-4 dark:bg-black-2 bg-gray-6 dark:placeholder-gray-5 dark:text-white placeholder-text-lg";

}
