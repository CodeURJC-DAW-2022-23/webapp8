import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/services/notification.service';
import { Notification } from '../notification.model';

/**
 * How to use this component:
 * 
 * <app-notification-list tab='tab'></app-notification-list>
 * tab values can be:
 * - 'all'
 * - 'mentions'
 */
@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.css']
})
export class NotificationListComponent {

  notifications: Notification[] = [];
  notificationCounter: number;

  @Input()
  tab: string;

  offset: number = 10;
  from: number = 0;
  showButtons: boolean = true;

  constructor(private router:Router, private service: NotificationService) {} 

  ngOnInit(): void {
    this.notificationCounter = 0;
    this.loadNotifications();
  }

  loadNotifications(){
    if (this.tab === "all") {
      this.service.getSomeNotifications(this.from, this.offset).subscribe(
        notifications => {
          if (notifications != null){
            notifications.forEach(n => this.notifications.push(n))
          } else {
            this.showButtons = false;
          }
        },
        error => console.log(error)
      );
    } else {
      this.service.getSomeMentions(this.from, this.offset).subscribe(
        notifications => {
          if (notifications != null){
            notifications.forEach(n => this.notifications.push(n))
          } else {
            this.showButtons = false;
          }
        },
        error => console.log(error)
      );
    }

    this.from += this.offset;
  }
}
