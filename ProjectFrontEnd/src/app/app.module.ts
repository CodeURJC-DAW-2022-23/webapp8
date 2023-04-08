import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'

import { AppComponent } from './app.component';
import { UserImageComponent } from './user-image/user-image.component';
import { SvgComponent } from './svg/svg.component';
import { BannerImageComponent } from './banner-image/banner-image.component';
import { ProfileButtonComponent } from './profile-button/profile-button.component';
import { ShowMoreButtonComponent } from './show-more-button/show-more-button.component';
import { WhiteButtonBlueBorderAndTextComponent } from './white-button-blue-border-and-text/white-button-blue-border-and-text.component';

@NgModule({
  declarations: [
    AppComponent,
    UserImageComponent,
    SvgComponent,
    BannerImageComponent,
    ProfileButtonComponent,
    ShowMoreButtonComponent,
    WhiteButtonBlueBorderAndTextComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
