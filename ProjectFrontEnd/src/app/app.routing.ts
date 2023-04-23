import { RouterModule } from '@angular/router';

import { ExplorerComponent } from './screens/explorer/explorer.component';
import { LoginComponent } from './screens/form/login/login.component';
import { SignUpComponent } from './screens/form/signup/signup.component';
import { VerificationComponent } from './screens/form/signup/verification/verification.component';
import { ErrorComponent } from './screens/error/error.component';
import { ConfirmationComponent } from './screens/form/forgot-password/forgot-password-confirmation/forgot-password-confirmation.component';
import { ForgotPassword } from './screens/form/forgot-password/forgot-password.component';
import { ProfileComponent } from './screens/profile/profile.component';
import { ResetPassword } from './screens/form/reset-password/reset-password.component';
import { ResetPasswordConfirmation } from './screens/form/reset-password/reset-password-confirmation/reset-password-confirmation.component';
import { DashboardComponent } from './screens/dashboard/dashboard.component';
import { SearchComponent } from './screens/search/search.component';
import { HomeComponent } from './screens/home/home.component';
import { IndexComponent } from "./screens/index/index.component";
import { BookmarksComponent } from './screens/bookmarks/bookmarks.component';
import { NotificationsComponent } from './screens/notifications/notifications.component';
import { WriteTweetComponent } from './screens/write-tweet/write-tweet.component';
import { ShowTweetComponent } from './screens/show-tweet/show-tweet.component';
import { FollowersComponent } from './screens/followers/followers.component';
import { EditProfileComponent } from './screens/profile/edit-profile/edit-profile.component';

const appRoutes = [
    { path: '', component: IndexComponent },
    { path: 'bookmarks', component: BookmarksComponent },
    { path: 'explore', component: ExplorerComponent },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignUpComponent },
    { path: 'verification', component: VerificationComponent },
    { path: 'error', component: ErrorComponent },
    { path: 'forgot-password-confirmation', component: ConfirmationComponent },
    { path: 'forgot-password', component: ForgotPassword },
    { path: 'reset-password/:passwordToken', component: ResetPassword },
    { path: 'reset-password-confirmation', component: ResetPasswordConfirmation },
    { path: 'profile/:id', component: ProfileComponent },
    { path: 'profile/:id/edit-profile', component: EditProfileComponent },
    { path: 'profile/:id/:users-list', component: FollowersComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'search/:keyword', component: SearchComponent },
    { path: 'home', component: HomeComponent },
    { path: 'notifications', component: NotificationsComponent },
    { path: 'write-tweet', component: WriteTweetComponent },
    { path: 'write-tweet/comment/:id', component: WriteTweetComponent },
    { path: 'tweet/:id', component: ShowTweetComponent },
]

export const AppRoutingModule = RouterModule.forRoot(appRoutes);
