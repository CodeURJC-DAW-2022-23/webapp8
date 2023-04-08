import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-banner-image',
  templateUrl: './banner-image.component.html',
  styleUrls: ['./banner-image.component.css']
})
export class BannerImageComponent {
  @Input()
  bannerSrc: string;
  @Input()
  bannerType: string;

  bannerClassMap = {
    "VIEW-PROFILE": "object-fill w-full h-full",
    "EDIT-PROFILE": "object-fill w-full h-full rounded-t-xl"
  };

  bannerClass: string;

  loadBannerClass(type: string){
    this.bannerClass = this.bannerClassMap[type]
  }
}
