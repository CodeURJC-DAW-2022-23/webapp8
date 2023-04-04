import { Component, Input } from '@angular/core';

/**
 * How to use this component:
 *
 * <app-user-image [type_img]="name_variable" [url_img]="src_variable"></app-user-image>
 *
 * The 'name_variable' can be:
 *        'PHOTO-PROFILE', 'PHOTO-LEFT-BAR', 'PHOTO-TWEET', 'PHOTO-NOTIFICATION'
 *
 * The 'src_variable' is the URL to the img
 */

@Component({
  selector: 'app-user-image',
  templateUrl: './user-image.component.html',
  styleUrls: ['./user-image.component.css']
})
export class UserImageComponent {

  @Input()
  type_img: string;
  @Input()
  url_img: string;

  class_figure_map = {
    'PHOTO-PROFILE': 'transition border-[6px] rounded-full cursor-pointer hover:scale-110 w-28 lmd:w-36 dark:border-black-0 border-white-0',
    'PHOTO-LEFT-BAR': 'w-9',
    'PHOTO-TWEET': 'mt-2 rounded-full h-11 w-11 hover:bg-black-0',
    'PHOTO-NOTIFICATION': 'w-8 duration-200 rounded-full hover:opacity-80 hover:bg-black-1'
  };

  class_img_map = {
    'PHOTO-PROFILE': 'object-fill w-full h-full rounded-full',
    'PHOTO-LEFT-BAR': 'rounded-full',
    'PHOTO-TWEET': 'w-full h-full rounded-full hover:opacity-80',
    'PHOTO-NOTIFICATION': 'object-contain w-full h-full rounded-full'
  };

  class_figure: string;
  class_img: string;

  loadClasses(type:string){
    this.class_figure = this.class_figure_map[type];
    this.class_img = this.class_img_map[type];
  }
}


