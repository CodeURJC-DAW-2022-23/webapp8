import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserComponent } from '../entities/user/user.component';
import { UserService } from './user.service';
import { User, UserInformation } from '../entities/user/user.model';


@Injectable({ providedIn: 'root' })
export class LoginService {

    logged: boolean;
    currentUser: UserInformation;

    constructor(private http: HttpClient, private service: UserService) {
        this.logged = false;
        this.reqIsLogged();
    }

    reqIsLogged() {
        let url = '/api/users/me';
        this.http.get(url, { withCredentials: true }).subscribe(
            response => {
                this.currentUser = response as UserInformation;
                this.logged = true;
            },
            error => {
                if (error.status != 404) {
                    console.error('Error when asking if logged: ' + JSON.stringify(error));
                }
            }
        );

    }

    logIn(user: string, pass: string) {
        let url = "api/login"
        this.http.post(url, { username: user, password: pass }, { withCredentials: true })
            .subscribe(
                (response) => this.reqIsLogged(),
                (error) => alert("Wrong credentials")
            );
    }

    logOut() {
        let url = "api/logout"
        return this.http.post(url, { withCredentials: true })
            .subscribe((resp: any) => {
                console.log("LOGOUT: Successfully");
                this.logged = false;
            });

    }

    isLogged() {
        return this.logged;
    }

    isAdmin() {
        return; //TODO fix roles Bug!!!
    }



}