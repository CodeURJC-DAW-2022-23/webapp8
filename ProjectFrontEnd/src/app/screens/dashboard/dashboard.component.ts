import { Component } from '@angular/core';
import { UserInformation } from 'src/app/entities/user/user.model';

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


  }
}
