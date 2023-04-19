import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable, catchError } from 'rxjs';
import { User, UserInformation } from '../entities/user/user.model';

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
  };

  getCurrentUser (): Observable<UserInformation> {
    let url = "/api/users/me"
    return this.http.get(url).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<UserInformation>;
  }

  getStatistics(): Observable<object> {
    let url = "/api/users/statistics";
    return this.http.get(url).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<object>;
  }

  getUsersToVerify(): Observable<UserInformation[]> {
    let url = "/api/users-to-verify";
    return this.http.get(url).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<UserInformation[]>;
  }

  getVerificatedUsers(): Observable<UserInformation[]> {
    let url = "/api/verificated-users";
    return this.http.get(url).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<UserInformation[]>;
  }

  getBannedUsers(): Observable<UserInformation[]> {
    let url = "/api/banned-users"
    return this.http.get(url).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<UserInformation[]>;
  }

  verifyUser(id: number) {
    let url = '/api/users/' + id + '?type=VERIFY';
    return this.http.put(url, null).pipe(
      catchError(error => this.handleError(error))
    )
  }

  unverifyUser(id: number) {
    let url = '/api/users/' + id + '?type=UNVERIFY';
    return this.http.put(url, null).pipe(
      catchError(error => this.handleError(error))
    )
  }

  unbannedUser(id: number) {
    let url = '/users/' + id + '?type=UNBAN';
    return this.http.put(url, null).pipe(
      catchError(error => this.handleError(error))
    )
  }

  handleError(error: any): any {
    throw new Error('Method not implemented.');
  }

}
