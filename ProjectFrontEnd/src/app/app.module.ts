import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'

import { AppComponent } from './app.component';
import { UserImageComponent } from './others/user-image/user-image.component';
import { SvgComponent } from './svg/svg.component';
import { BannerImageComponent } from './others/banner-image/banner-image.component';
import { ProfileButtonComponent } from './others/profile-button/profile-button.component';
import { ShowMoreButtonComponent } from './others/show-more-button/show-more-button.component';
import { WhiteButtonBlueBorderAndTextComponent } from './others/white-button-blue-border-and-text/white-button-blue-border-and-text.component';
import { WhiteButtonBlackBorderAndTextComponent } from './others/white-button-black-border-and-text/white-button-black-border-and-text.component';
import { LogOutButtonComponent } from './others/log-out-button/log-out-button.component';
import { ThemeSwitcherButtonComponent } from './others/theme-switcher-button/theme-switcher-button.component';
import { CanButtonComponent } from './others/can-button/can-button.component';
import { GoBackButtonComponent } from './others/go-back-button/go-back-button.component';
import { LeftBarButtonComponent } from './others/left-bar-button/left-bar-button.component';
//import { LeftBarComponent } from './left-bar/left-bar.component';
import { BrandButtonComponent } from './others/brand-button/brand-button.component';
import { BlueButtonComponent } from './others/blue-button/blue-button.component';
import { RedButtonComponent } from './others/red-button/red-button.component';
import { ButtonWithoutBackgroundWhiteBorderAndTextComponent } from './others/button-without-background-white-border-and-text/button-without-background-white-border-and-text.component';
import { FloatingInputComponent } from './others/floating-input/floating-input.component';
import { NavBarButtonComponent } from './others/nav-bar-button/nav-bar-button.component';
import { SearcherComponent } from './others/searcher/searcher.component';
import { TweetComponent } from './entities/tweet/tweet.component';

@NgModule({
  declarations: [
    AppComponent,
    UserImageComponent,
    SvgComponent,
    BannerImageComponent,
    ProfileButtonComponent,
    ShowMoreButtonComponent,
    WhiteButtonBlueBorderAndTextComponent,
    WhiteButtonBlackBorderAndTextComponent,
    LogOutButtonComponent,
    ThemeSwitcherButtonComponent,
    CanButtonComponent,
    GoBackButtonComponent,
    LeftBarButtonComponent,
    //LeftBarComponent,
    BrandButtonComponent,
    BlueButtonComponent,
    RedButtonComponent,
    ButtonWithoutBackgroundWhiteBorderAndTextComponent,
    FloatingInputComponent,
    NavBarButtonComponent,
    SearcherComponent,
    TweetComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }