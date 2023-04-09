import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-can-button',
  templateUrl: './can-button.component.html',
  styleUrls: ['./can-button.component.css']
})
export class CanButtonComponent {
  @Input()
  idElement: string;

  deleteElement(id: string) {

  }
}
