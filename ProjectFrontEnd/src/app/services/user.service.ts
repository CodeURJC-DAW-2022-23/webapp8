import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable, catchError } from 'rxjs';
import { UserInformation } from '../entities/tweet/tweet.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUser (username: string): Observable<UserInformation> {
    let url = "/api/users/" + username
    return this.http.get(url).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<UserInformation>;
  }

  handleError(error: any): any {
    throw new Error('Method not implemented.');
  }

}
