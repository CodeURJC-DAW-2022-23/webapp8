import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from '@angular/router'; // To route the page when needed
import { Signup } from "src/app/services/signup.service";
import { UserService } from "src/app/services/user.service";

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css']
  })

export class SignUpComponent{


    constructor(private router:Router,activatedRoute: ActivatedRoute, private service: Signup) {}
       
        signUpUser(event: any, username:string, password: string, email:string){
            event.preventDefault();
            let done: Boolean;
            done = this.service.signUp(username,password, email);
            if (done){
                this.router.navigate(['/verification']);
            }else{
                
            }
        }
    }
