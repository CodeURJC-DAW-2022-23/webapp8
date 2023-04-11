
import { Injectable } from '@angular/core';
import { environment } from 'src/enviroment/enviroment';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Hahstag } from '../entities/hashtag/hashtag.interface';
import { hashtagComponent } from '../entities/hashtag/hashtag.component';

@Injectable({
    providedIn: 'root'
})
export class HashtagService{
    constructor(
        private http: HttpClient,
    ){}

    getSomeTrends(): Observable<hashtagComponent[]> {
        let url = "https://localhost:8443/trends?from=0&size=10"
        return this.http.get(url).pipe(
            map(response => this.extractHashtag(response as any))
        );
    }

    private extractHashtag(response: any) {
        return response.items;
    }

    private handleError(error: any) {
		console.error(error);
		return throwError("Server error (" + error.status + "): " + error.text())
	}
}