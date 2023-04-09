import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-white-button-black-border-and-text',
  templateUrl: './white-button-black-border-and-text.component.html',
  styleUrls: ['./white-button-black-border-and-text.component.css']
})
export class WhiteButtonBlackBorderAndTextComponent {
  @Input()
  srcButton: string;
  @Input()
  textButton: string;
}
