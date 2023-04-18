
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { hashtagComponent } from '../entities/hashtag/hashtag.component';
import { Tweet, TweetInformation } from '../entities/tweet/tweet.model';
import { TweetComponent } from '../entities/tweet/tweet.component';
import { Hashtag } from '../entities/hashtag/hashtag.model';

@Injectable({
    providedIn: 'root'
})
export class HashtagService{
    constructor(
        private http: HttpClient,
    ){}
    
    getSomeTrends(offset:number, size:number): Observable<Hashtag[]> {
        let url = "/api/trends?from="+ offset + "&size=" + size;
        return this.http.get(url).pipe(
          catchError(error => this.handleError(error)
        )) as Observable<Hashtag[]>
      }

      getTweetsAssociatedToAHashtag(hashtag:String): Observable <TweetInformation[]>{
        let url = "api/hashtag/" + hashtag + "/tweets?from=0&size=10";
        return this.http.get(url).pipe(
          catchError(error => this.handleError(error)
        )) as Observable<TweetInformation[]>
      }
    
      

    private handleError(error: any) {
		console.error(error);
		return throwError("Server error (" + error.status + "): " + error.text())
	}
}