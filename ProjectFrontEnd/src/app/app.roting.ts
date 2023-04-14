import { Routes, RouterModule } from '@angular/router';

import { explorer } from './screens/explorer/explorer.component';
import { LoginComponent } from './screens/form/login/login.component';
import { TweetComponent } from './entities/tweet/tweet.component';

const appRoutes = [
    { path: 'explorer', component: explorer },
    { path: 'login', component: LoginComponent },
    
]

export const routing = RouterModule.forRoot(appRoutes);