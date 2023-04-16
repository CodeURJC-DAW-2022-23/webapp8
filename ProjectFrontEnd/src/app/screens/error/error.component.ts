import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { LoginService } from "src/app/services/login.service";

@Component({
    selector: 'app-error',
    templateUrl: './error.component.html',
    styleUrls: ['./error.component.css']
  })
export class Error {

    constructor(private router:Router, private service: LoginService) {}
    refresh(): void {
        window.location.reload();
    }

    logOut(){
        this.service.logOut();
    }
}
