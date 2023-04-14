
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Tweet } from '../entities/tweet/tweet.model';

@Injectable({
    providedIn: 'root'
})
export class TweetService{
    constructor(
        private httpClient: HttpClient,
    ){}
    
    getTweetsOfAUser(userId, from, size): Observable<Tweet[]> {
		let url = "/api/users/" + userId + "/tweets?from=" + from + "&size=" + size
		return this.httpClient.get(url).pipe(
			catchError(error => this.handleError(error))
		) as Observable<Tweet[]>;
	}

    getBookmarksOfCurrentUser(from, size): Observable<Tweet[]> {
		let url = "/api/bookmarks?from=" + from + "&size=" + size
		return this.httpClient.get(url).pipe(
			catchError(error => this.handleError(error))
		) as Observable<Tweet[]>;
	}

    getTweetsOfAHashtag(hashtag, from, size): Observable<Tweet[]> {
		let url = "/api/hashtag/DAW/tweets?from=" + from + "&size=" + size
		return this.httpClient.get(url).pipe(
			catchError(error => this.handleError(error))
		) as Observable<Tweet[]>;
	}

    getTweetsForCurrentUser(from, size): Observable<Tweet[]> {
		let url = "/api/tweets?from=" + from + "&size=" + size
		return this.httpClient.get(url).pipe(
			catchError(error => this.handleError(error))
		) as Observable<Tweet[]>;
	}

    private handleError(error: any) {
		console.error(error);
		return throwError("Server error (" + error.status + "): " + error.text())
	}
}