import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { TweetService } from 'src/app/services/tweet-service';
import { Tweet, TweetInformation } from '../tweet.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-tweet-list',
  templateUrl: './tweet-list.component.html',
  styleUrls: ['./tweet-list.component.css']
})
export class TweetListComponent {
  @Input()
  isLogged: boolean;
  tweets: TweetInformation[] = [];
  id: number;
  size: number = 10;
  offset: number = 0;
  @Input()
  typeElement: string;
  @Input()
  hashtag: string;
  @Input()
  userId: number;

  constructor(private router: Router, private service: TweetService, private userService: UserService) { }

  ngOnInit() {
    this.userService.getCurrentUser().subscribe(
      user => {
        this.isLogged = user !== null;
        this.id = user.user.id;
      },
      error => console.log(error)
    );
    this.getTweetsForCurrentPage();
    this.offset += 10;
  }

  loadMoreElements() {
    this.addSpinner();
    this.getTweetsForCurrentPage();
    this.offset += 10;
  }

  addSpinner() {
    document.getElementById("spinner").innerHTML = `<div class="flex items-center justify-center sticky">
                <div class="inline-block h-8 w-8 animate-spin rounded-full border-4 border-primary border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]" role="status">
                    <span class="!absolute !-m-px !h-px !w-px !overflow-hidden !whitespace-nowrap !border-0 !p-0 ![clip:rect(0,0,0,0)]">
                        Loading...
                    </span>
                </div>
            </div>`
  }

  removeSpinner() {
    document.getElementById("spinner").innerHTML = ``
  }

  hideButtons() {
    document.getElementById("LoadMore").classList.add("hidden");
    document.getElementById("LoadMoreMobile").classList.add("hidden");
    document.getElementById("LoadMore").classList.remove('llg:block');
  }

  getTweetsForCurrentPage() {
    switch (this.typeElement) {
      case "Home":
        this.service.getTweetsForCurrentUser(this.offset, this.size).subscribe(
          tweet => {
            if (tweet !== null) {
              tweet.forEach(t => this.tweets.push(t))
            } else {
              this.hideButtons()
            }
            this.removeSpinner()
          },
          error => console.error(error)
        );
        break;
      case "Profile":
        this.service.getTweetsOfAUser(this.userId, this.offset, this.size).subscribe(
          tweet => {
            if (tweet !== null) {
              tweet.forEach(t => this.tweets.push(t))
            } else {
              this.hideButtons()
            }
            this.removeSpinner()
          },
          error => console.error(error)
        );
        break;
      case "Bookmarks":
        this.service.getBookmarksOfCurrentUser(this.offset, this.size).subscribe(
          tweet => {
            if (tweet !== null) {
              tweet.forEach(t => this.tweets.push(t))
            } else {
              this.hideButtons()
            }
            this.removeSpinner()
          },
          error => console.error(error)
        );
        break;
      case "Hashtag":
        this.service.getTweetsOfAHashtag(this.hashtag, this.offset, this.size).subscribe(
          tweet => {
            if (tweet !== null) {
              tweet.forEach(t => this.tweets.push(t))
            } else {
              this.hideButtons()
            }
            this.removeSpinner()
          },
          error => console.error(error)
        );
        break;
    }

  }
}