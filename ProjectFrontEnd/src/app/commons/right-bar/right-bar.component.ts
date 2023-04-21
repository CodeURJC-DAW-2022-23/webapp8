import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Hashtag } from 'src/app/entities/hashtag/hashtag.model';
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
  hashtagList:Hashtag[] = [];

  offset:number = 0;
  size:number = 5;
  isExplorePage:boolean;
  isLogged:boolean;
  showRecommended:boolean;

  constructor(private router:Router, private logService: LoginService, private userService: UserService, private hashtagService:HashtagService) {}

  ngOnInit(): void {
    this.isExplorePage = this.router.url.includes("explorer");
    this.showRecommended = this.isLogged && this.isExplorePage;
    this.isLogged = this.logService.isLogged();
    this.getTrends();
    if (this.isLogged) {
      this.getRecommendedUsers();
    }
  }
  getTrends() {
    this.hashtagService.getSomeTrends(this.offset, this.size).subscribe(
      response => response.forEach(h => this.hashtagList.push(h)),
      error => this.hashtagList = []
    );
  }
  getRecommendedUsers() {
    this.userService.getRecommendedUsers().subscribe(
      users => { users.forEach(u => this.recommendedUsers.push(u)) },
      error => console.log(error)
    );
  }
}
