import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TweetInformation } from 'src/app/entities/tweet/tweet.model';
import { TweetService } from 'src/app/services/tweet-service';

@Component({
  selector: 'app-bookmarks',
  templateUrl: './bookmarks.component.html',
  styleUrls: ['./bookmarks.component.css']
})
export class BookmarksComponent {
  tweets: TweetInformation[]

  constructor(private tweetService: TweetService, activatedRoute: ActivatedRoute) {}
}
