import { Routes, RouterModule } from '@angular/router';

import { explorer } from './screens/explorer/explorer.component';
import { LoginComponent } from './screens/form/login/login.component';
import { TweetComponent } from './entities/tweet/tweet.component';
import { SignUpComponent } from './screens/form/signup/signup.component';
import { Verification } from './screens/form/signup/verification/verification.component';
import { Error } from './screens/error/error.component';
import { confirmation } from './screens/form/forgot-password/forgot-password-confirmation/forgot-password-confirmation.component';
import { ForgotPassword } from './screens/form/forgot-password/forgot-password.component';
import { ProfileComponent } from './screens/profile/profile.component';
import { ResetPassword } from './screens/form/reset-password/reset-password.component';
import { ResetPasswordConfirmation } from './screens/form/reset-password/reset-password-confirmation/reset-password-confirmation.component';
import { DashboardComponent } from './screens/dashboard/dashboard.component';
import { SearcherComponent } from './others/searcher/searcher.component';
import { Search } from './screens/search/search.component';

import { IndexComponent } from "./screens/index/index.component";
import { BookmarksComponent } from './screens/bookmarks/bookmarks.component';

const appRoutes = [
    { path: 'index', component: IndexComponent },
    { path: 'bookmarks', component: BookmarksComponent },
    { path: 'explorer', component: explorer },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignUpComponent},
    { path: 'verification', component: Verification},
    { path: 'error', component: Error},
    { path: 'forgot-password-confirmation', component: confirmation },
    { path: 'forgot-password', component: ForgotPassword},
    { path: 'reset-password', component: ResetPassword },
    { path: 'reset-password-confirmation', component: ResetPasswordConfirmation },
    { path: 'profile/:id', component: ProfileComponent},
    { path: 'dashboard', component: DashboardComponent},
    { path: 'search/:keyword', component: Search }, //check
]

export const routing = RouterModule.forRoot(appRoutes);
