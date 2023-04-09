import { Component, Input } from '@angular/core';

/**
 *  HOW TO USE THIS COMPONENT:
 *
 *  <app-log-out-button [srcButton]="src"></app-log-out-button>
 */
@Component({
  selector: 'app-log-out-button',
  templateUrl: './log-out-button.component.html',
  styleUrls: ['./log-out-button.component.css']
})
export class LogOutButtonComponent {
  @Input()
  srcButton: string;
}
