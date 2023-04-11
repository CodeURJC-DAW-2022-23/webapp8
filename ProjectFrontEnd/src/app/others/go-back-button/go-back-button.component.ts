import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-go-back-button',
  templateUrl: './go-back-button.component.html',
  styleUrls: ['./go-back-button.component.css']
})
export class GoBackButtonComponent {
  @Input()
  srcButton: string;

  goBack(where: string) {

  };
}
