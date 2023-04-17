import { Component } from '@angular/core';
import { UserInformation } from 'src/app/entities/user/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  users: UserInformation[];
  isStadistics: boolean;
  requestsClass: string;
  verfiedClass: string;
  bannedClass: string;
  stadisticsClass: string;

  constructor(private userService: UserService){
    this.userService.getUsersToVerify().subscribe(
      users => this.users = users
    );
  }


  showPage(tabToShow, tabsToHide, isStadistics) {
    this.isStadistics = isStadistics;
    let tabSelected = document.getElementById(tabToShow);
    tabSelected.classList.remove(
      "dark:text-gray-5",
      "font-semibold",
      "border-transparent"
    );
    tabSelected.classList.add(
      "font-bold",
      "dark:text-white-0",
      "text-black-0",
      "border-primary"
    );

    for (let tabToHide of tabsToHide) {
      let tabHidden = document.getElementById(tabToHide);
      tabHidden.classList.remove(
        "font-bold",
        "dark:text-white-0",
        "text-black-0",
        "border-primary"
      );
      tabHidden.classList.add(
        "dark:text-gray-5",
        "font-semibold",
        "border-transparent"
      );
    }

    if (this.isStadistics) {
      return;
    }

    this.loadUsers(tabToShow);
  }

  private loadUsers(tabToShow: string) {
    if ('requests-tab' === tabToShow ) {
      this.userService.getUsersToVerify().subscribe(
        users => this.users = users
      );
      return;
    }

    if ('verificated-tab' === tabToShow ) {
      this.userService.getVerificatedUsers().subscribe(
        users => this.users = users
      );
      return;
    }

    if ('banned-tab' === tabToShow ) {
      this.userService.getBannedUsers().subscribe(
        users => this.users = users
      );
    }
  }
}
