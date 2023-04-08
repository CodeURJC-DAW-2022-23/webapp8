import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'

import { AppComponent } from './app.component';
import { UserImageComponent } from './user-image/user-image.component';
import { SvgComponent } from './svg/svg.component';
import { BannerImageComponent } from './banner-image/banner-image.component';

@NgModule({
  declarations: [
    AppComponent,
    UserImageComponent,
    SvgComponent,
    BannerImageComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
