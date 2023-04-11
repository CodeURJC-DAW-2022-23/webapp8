import { Component } from '@angular/core';

/**
 *  HOW TO USE THIS COMPONENT:
 *
 * <app-theme-switcher-button></app-theme-switcher-button>
 */
@Component({
  selector: 'app-theme-switcher-button',
  templateUrl: './theme-switcher-button.component.html',
  styleUrls: ['./theme-switcher-button.component.css']
})
export class ThemeSwitcherButtonComponent {
  loadThemePage() {
    var themeToggleDarkIcon = document.getElementById("theme-toggle-dark-icon");
    var themeToggleLightIcon = document.getElementById("theme-toggle-light-icon");

    // On page load or when changing themes, best to add inline in `head` to avoid FOUC
    if (
      localStorage.getItem("color-theme") === "dark" ||
      (!("color-theme" in localStorage) &&
        window.matchMedia("(prefers-color-scheme: dark)").matches)
    ) {
      document.documentElement.classList.add("dark");
    } else {
      document.documentElement.classList.remove("dark");
    }

    // Change the icons inside the button based on previous settings
    if (
      localStorage.getItem("color-theme") === "dark" ||
      (!("color-theme" in localStorage) &&
        window.matchMedia("(prefers-color-scheme: dark)").matches)
    ) {
      themeToggleDarkIcon.classList.remove("hidden");
    } else {
      themeToggleLightIcon.classList.remove("hidden");
    }
  };

  changeThemePage() {
    var themeToggleDarkIcon = document.getElementById("theme-toggle-dark-icon");
    var themeToggleLightIcon = document.getElementById("theme-toggle-light-icon");

    // toggle icons inside button
    themeToggleDarkIcon.classList.toggle("hidden");
    themeToggleLightIcon.classList.toggle("hidden");

    // if set via local storage previously
    if (localStorage.getItem("color-theme")) {
      if (localStorage.getItem("color-theme") === "light") {
        document.documentElement.classList.add("dark");
        localStorage.setItem("color-theme", "dark");
      } else {
        document.documentElement.classList.remove("dark");
        localStorage.setItem("color-theme", "light");
      }

      // if NOT set via local storage previously
    } else {
      if (document.documentElement.classList.contains("dark")) {
        document.documentElement.classList.remove("dark");
        localStorage.setItem("color-theme", "light");
      } else {
        document.documentElement.classList.add("dark");
        localStorage.setItem("color-theme", "dark");
      }
    }
  }

}

