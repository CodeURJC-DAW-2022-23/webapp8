import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { routing } from './app.roting';

import { UserImageComponent } from './others/user-image/user-image.component';
import { BannerImageComponent } from './others/banner-image/banner-image.component';
import { ShowMoreButtonComponent } from './others/show-more-button/show-more-button.component';
import { WhiteButtonBlueBorderAndTextComponent } from './others/white-button-blue-border-and-text/white-button-blue-border-and-text.component';
import { WhiteButtonBlackBorderAndTextComponent } from './others/white-button-black-border-and-text/white-button-black-border-and-text.component';
import { LogOutButtonComponent } from './others/log-out-button/log-out-button.component';
import { ThemeSwitcherButtonComponent } from './others/theme-switcher-button/theme-switcher-button.component';
import { CanButtonComponent } from './others/can-button/can-button.component';
import { GoBackButtonComponent } from './others/go-back-button/go-back-button.component';
import { BrandButtonComponent } from './others/brand-button/brand-button.component';
import { BlueButtonComponent } from './others/blue-button/blue-button.component';
import { RedButtonComponent } from './others/red-button/red-button.component';
import { ButtonWithoutBackgroundWhiteBorderAndTextComponent } from './others/button-without-background-white-border-and-text/button-without-background-white-border-and-text.component';
import { FloatingInputComponent } from './others/floating-input/floating-input.component';
import { NavBarButtonComponent } from './others/nav-bar-button/nav-bar-button.component';
import { SearcherComponent } from './others/searcher/searcher.component';
import { TweetComponent } from './entities/tweet/tweet.component';
import { explorer } from './screens/explorer/explorer.component';
import { hashtagComponent } from './entities/hashtag/hashtag.component';
import { NotificationComponent } from './entities/notification/notification.component';
import { TweetListComponent } from './entities/tweet/tweet-list/tweet-list.component';
import { UserComponent } from './entities/user/user.component';
import { ProfileComponent } from './screens/profile/profile.component';
import { LoginComponent } from './screens/form/login/login.component';
import { SignUpComponent } from './screens/form/signup/signup.component';
import { Verification } from './screens/form/signup/verification/verification.component';
import { Error } from './screens/error/error.component';
import { confirmation } from './screens/form/forgot-password/forgot-password-confirmation/forgot-password-confirmation.component';
import { ForgotPassword } from './screens/form/forgot-password/forgot-password.component';
import { ResetPassword } from './screens/form/reset-password/reset-password.component';
import { ResetPasswordConfirmation } from './screens/form/reset-password/reset-password-confirmation/reset-password-confirmation.component';
import { Search } from './screens/search/search.component';
import { NotificationsComponent } from './screens/notifications/notifications.component';
import { DashboardComponent } from './screens/dashboard/dashboard.component';
import { HomeComponent } from './screens/home/home.component';
import { IndexComponent } from './screens/index/index.component';
import { BookmarksComponent } from './screens/bookmarks/bookmarks.component';
import { WriteTweetComponent } from './screens/write-tweet/write-tweet.component';
import { LeftBarComponent } from './commons/left-bar/left-bar.component';
import { NotificationListComponent } from './entities/notification/notification-list/notification-list.component';
import { ShowTweetComponent } from './screens/show-tweet/show-tweet.component';
import { FollowersComponent } from './screens/followers/followers.component';
import { EditProfileComponent } from './screens/profile/edit-profile/edit-profile.component';
@NgModule({
  declarations: [
    AppComponent,
    UserImageComponent,
    BannerImageComponent,
    ShowMoreButtonComponent,
    WhiteButtonBlueBorderAndTextComponent,
    WhiteButtonBlackBorderAndTextComponent,
    LogOutButtonComponent,
    ThemeSwitcherButtonComponent,
    CanButtonComponent,
    GoBackButtonComponent,
    BrandButtonComponent,
    BlueButtonComponent,
    RedButtonComponent,
    ButtonWithoutBackgroundWhiteBorderAndTextComponent,
    FloatingInputComponent,
    NavBarButtonComponent,
    SearcherComponent,
    TweetComponent,
    explorer,
    hashtagComponent,
    NotificationComponent,
    TweetListComponent,
    UserComponent,
    ProfileComponent,
    LoginComponent,
    SignUpComponent,
    Verification,
    Error,
    confirmation,
    ForgotPassword,
    ResetPassword,
    ResetPasswordConfirmation,
    Search,
    DashboardComponent,
    NotificationsComponent,
    IndexComponent,
    BookmarksComponent,
    HomeComponent,
    WriteTweetComponent,
    LeftBarComponent,
    NotificationListComponent,
    ShowTweetComponent,
    EditProfileComponent,
    FollowersComponent,
    EditProfileComponent,
  ],
  imports: [
    FormsModule,
    HttpClientModule,
    NgxChartsModule,
    BrowserAnimationsModule,
    routing
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
