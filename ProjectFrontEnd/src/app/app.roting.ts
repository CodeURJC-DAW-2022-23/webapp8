import { Routes, RouterModule } from '@angular/router';

import { explorer } from './screens/explorer/explorer.component';
import { LoginComponent } from './screens/form/login/login.component';
import { TweetComponent } from './entities/tweet/tweet.component';
import { SignUpComponent } from './screens/form/signup/signup.component';
import { Verification } from './screens/form/signup/verification/verification.component';
import { Error } from './screens/error/error.component';

const appRoutes = [
    { path: 'explorer', component: explorer },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignUpComponent},
    { path: 'verification', component: Verification},
    { path: 'error', component: Error}
    
]

export const routing = RouterModule.forRoot(appRoutes);