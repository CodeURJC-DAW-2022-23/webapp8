import { Component, Input } from '@angular/core';

/**
 *  HOW TO USE THIS COMPONENT:
 *
 * <app-can-button [idElement]="src"></app-can-button>
 */
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
