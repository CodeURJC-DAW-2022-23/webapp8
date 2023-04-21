import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserInformation } from 'src/app/entities/user/user.model';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-right-bar',
  templateUrl: './right-bar.component.html',
  styleUrls: ['./right-bar.component.css']
})
export class RightBarComponent implements OnInit {

  recommendedUsers: UserInformation[] = [];

  constructor(private router:Router, private logService: LoginService, private userService: UserService) {}

  ngOnInit(): void {
    if (this.logService.isLogged ) {
      this.getRecommendedUsers()
    }
  }
  getRecommendedUsers() {
    this.userService.getRecommendedUsers().subscribe(
      users => { users.forEach(u => this.recommendedUsers.push(u)) },
      error => console.log(error)
    );
  }
}
