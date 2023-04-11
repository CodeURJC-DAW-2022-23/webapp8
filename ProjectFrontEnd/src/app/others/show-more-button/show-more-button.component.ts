import { Component, Input } from '@angular/core';

/**
 *  HOW TO USE THIS COMPONENT:
 *
 *  <app-show-more-button [typeElement]="typeElement" [userId]="userId" [hashtagId]="hashtagId"></app-show-more-button>
 *
 *
 */
@Component({
  selector: 'app-show-more-button',
  templateUrl: './show-more-button.component.html',
  styleUrls: ['./show-more-button.component.css']
})
export class ShowMoreButtonComponent {
  @Input()
  typeElement: string;
  @Input()
  userId: number = 5;
  @Input()
  hashtagId: string = "DAW";

  URL = "https://localhost:8443/api";
  counterPetitions = 0
  NUMBER_ELEMENTS_PER_LOAD = 10;
  urlMap = {
    "TWEETS-OF-A-USER": `/users/${this.userId}/tweets`,
    "TWEETS-FOR-A-USER": `/tweets`,
    "TWEETS-BOOKMARKED": `/bookmarks`,
    "TWEETS-ASSOCIATED-A-HASHTAG": `/hashtag/${this.hashtagId}/tweets`,
    "NOTIFICATIONS": `/notifications`,
    "MENTIONS": `/mentions`,
    "TRENDS": `/trends`
  }

  loadMoreElements(type: string){
    let url = this.URL + this.urlMap[type] + `?from=${this.counterPetitions}&size=${this.NUMBER_ELEMENTS_PER_LOAD}`;
    console.log(url);
    this.counterPetitions++;
  }
}
