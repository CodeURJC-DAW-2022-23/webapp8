import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  show:boolean;
  constructor(private router: Router){}
  ngOnInit(): void {
      this.changePage();
  }
  changePage(){
    this.show = !(this.router.url.includes("error") || this.router.url.includes("login") || this.router.url.includes("register") || this.router.url === "/" || this.router.url.includes("verification") || this.router.url.includes("reset-password") || this.router.url.includes("forgot-password") || this.router.url.includes("write-tweet"))
  }
}
