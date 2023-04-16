import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UserService } from "./user.service";

@Injectable({ providedIn: 'root' })
export class Signup {

    constructor(private http: HttpClient, private service: UserService) {}

    signUp(user:string, pass: string, mail:string):any{
        let url = "api/signup"
        this.http.post(url, {username: user, password: pass, email: mail}, {withCredentials: true})
        .subscribe(
            (response) => {return true}, // Change it later
            (error) => {return false}
        );
    }
}
