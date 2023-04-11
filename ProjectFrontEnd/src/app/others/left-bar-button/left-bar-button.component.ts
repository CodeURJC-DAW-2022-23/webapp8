import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-left-bar-button',
  templateUrl: './left-bar-button.component.html',
  styleUrls: ['./left-bar-button.component.css']
})
export class LeftBarButtonComponent {
  @Input()
  typeButton: string;

  textButton: string;
  textButtonMap = {
    "HOME": "Home",
    "EXPLORE": "Explore",
    "NOTIFICATIONS": "Notifications",
    "BOOKMARKS": "Bookmarks"
  };
  svgMap = {
    "HOME": `
    <svg class="w-8 dark:fill-white-0 fill-black-0" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
    aria-hidden="true">
      <g>
        <path
        d="M12 9c-2.209 0-4 1.791-4 4s1.791 4 4 4 4-1.791 4-4-1.791-4-4-4zm0 6c-1.105 0-2-.895-2-2s.895-2 2-2 2 .895 2 2-.895 2-2 2zm0-13.304L.622 8.807l1.06 1.696L3 9.679V19.5C3 20.881 4.119 22 5.5 22h13c1.381 0 2.5-1.119 2.5-2.5V9.679l1.318.824 1.06-1.696L12 1.696zM19 19.5c0 .276-.224.5-.5.5h-13c-.276 0-.5-.224-.5-.5V8.429l7-4.375 7 4.375V19.5z">
        </path>
      </g>
    </svg>
    `,
    "EXPLORE": `
    <svg class="w-8 dark:fill-white-0" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" aria-hidden="true">
      <g>
        <path
          d="M10.09 3.098L9.72 7h5.99l.39-4.089 1.99.187L17.72 7h3.78v2h-3.97l-.56 6h3.53v2h-3.72l-.38 4.089-1.99-.187.36-3.902H8.78l-.38 4.089-1.99-.187L6.77 17H2.5v-2h4.46l.56-6H3.5V7h4.21l.39-4.089 1.99.187zM14.96 15l.56-6H9.53l-.56 6h5.99z">
        </path>
      </g>
    </svg>
    `,
    "NOTIFICATIONS": `
    <svg class="w-8 dark:fill-white-0 fill-black-0" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
      aria-hidden="true">
      <g>
        <path
          d="M19.993 9.042C19.48 5.017 16.054 2 11.996 2s-7.49 3.021-7.999 7.051L2.866 18H7.1c.463 2.282 2.481 4 4.9 4s4.437-1.718 4.9-4h4.236l-1.143-8.958zM12 20c-1.306 0-2.417-.835-2.829-2h5.658c-.412 1.165-1.523 2-2.829 2zm-6.866-4l.847-6.698C6.364 6.272 8.941 4 11.996 4s5.627 2.268 6.013 5.295L18.864 16H5.134z">
        </path>
      </g>
    </svg>
    `,
    "BOOKMARKS": `
    <svg class="w-8 fill-black-0 dark:fill-white-0" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
      aria-hidden="true">
      <g>
        <path
          d="M4 4.5C4 3.12 5.119 2 6.5 2h11C18.881 2 20 3.12 20 4.5v18.44l-8-5.71-8 5.71V4.5zM6.5 4c-.276 0-.5.22-.5.5v14.56l6-4.29 6 4.29V4.5c0-.28-.224-.5-.5-.5h-11z">
        </path>
      </g>
    </svg>
    `
  };

  ngOnInit() {
    this.textButton = this.textButtonMap[this.typeButton];
    let svgContainer = document.getElementsByTagName('figure')[0]; //document.getElementById(this.typeButton);
    svgContainer.innerHTML = this.svgMap[this.typeButton];
  }

}
