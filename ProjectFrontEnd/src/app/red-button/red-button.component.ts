import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-red-button',
  templateUrl: './red-button.component.html',
  styleUrls: ['./red-button.component.css']
})
export class RedButtonComponent {
  @Input()
  href: string;
  @Input()
  text: string;

  redButtonClassMap = {
    "Unfollow":" px-4 py-2 font-bold",
    "Ban user":" px-4 py-2 font-bold",
    "Unban":" px-3 py-2 text-sm font-medium",
    "Unverify":" px-3 py-2 text-sm font-medium"
  };

  red_button:string = "text-center transition rounded-full cursor-pointer text-white-0 bg-red-3 hover:scale-110";

  goTo() {
    window.location.href = this.href
  }

  ngOnInit(){
    this.red_button += this.redButtonClassMap[this.text];
  }
}
