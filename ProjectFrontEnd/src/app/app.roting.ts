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

const appRoutes = [
    { path: 'explorer', component: explorer },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignUpComponent},
    { path: 'verification', component: Verification},
    { path: 'error', component: Error},
    { path: 'forgot-password-confirmation', component: confirmation },
    { path: 'forgot-password', component: ForgotPassword},
    { path: 'profile/:id', component: ProfileComponent}

]

export const routing = RouterModule.forRoot(appRoutes);
