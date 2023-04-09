import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-brand-button',
  templateUrl: './brand-button.component.html',
  styleUrls: ['./brand-button.component.css']
})
export class BrandButtonComponent {
  @Input()
  typeBrand: string;
  @Input()
  aSrc: string;

  aClass: string;
  svgClass: string;

  aClassMap = {
    "LEFT-BAR": "flex justify-center mt-6 transition hover:scale-150",
    "ERROR": "transition rounded-full cursor-pointer hover:scale-150",
    "OTHER": "transition rounded-full cursor-pointer hover:scale-150"
  }
  svgClassMap = {
    "LEFT-BAR": "fill-primary w-11",
    "ERROR": "w-14 fill-primary",
    "OTHER": "w-11 fill-primary"
  }

  ngOnInit() {
    this.aClass = this.aClassMap[this.typeBrand];
    this.svgClass = this.svgClassMap[this.typeBrand];
  }

}
