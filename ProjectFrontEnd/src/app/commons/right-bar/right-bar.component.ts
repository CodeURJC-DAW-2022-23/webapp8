import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Trend } from 'src/app/entities/trend/trend.model';
import { UserInformation } from 'src/app/entities/user/user.model';
import { HashtagService } from 'src/app/services/hashtag.service';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-right-bar',
  templateUrl: './right-bar.component.html',
  styleUrls: ['./right-bar.component.css']
})
export class RightBarComponent implements OnInit {

  recommendedUsers: UserInformation[] = [];
  trends:Trend[] = [];

  offset:number = 0;
  size:number = 5;
  isExplorePage:boolean;
  isLogged:boolean;
  showRecommended:boolean;

  constructor(private router:Router, private userService: UserService, private hashtagService:HashtagService) {}

  ngOnInit(): void {
    this.isExplorePage = this.router.url.includes("explore");
    this.showRecommended = this.isLogged && this.isExplorePage;
    this.userService.getCurrentUser().subscribe(
      response => this.isLogged = response !== null
    );
    this.getTrends();
    this.getRecommendedUsers();
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.isExplorePage = this.router.url.includes("explore");
        this.showRecommended = this.isLogged && this.isExplorePage;
      }
    })
  }
  getTrends() {
    this.hashtagService.getSomeTrends(this.offset, this.size).subscribe(
      response => response.forEach(h => this.trends.push(h)),
      error => this.trends = []
    );
  }
  getRecommendedUsers() {
    this.userService.getRecommendedUsers().subscribe(
      users => { users.forEach(u => this.recommendedUsers.push(u)) },
      error => console.log(error)
    );
  }
}
