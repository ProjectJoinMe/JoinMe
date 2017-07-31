import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home';
import { CreateRideComponent } from './rides/create_ride';
import { SearchRideComponent } from './rides/search_ride';
import { AboutComponent } from './about';
import { NoContentComponent } from './no-content';

import { DataResolver } from './AppResolver';
import {RegistrationComponent} from "./registration/RegistrationComponent";
import {LoginComponent} from "./login/LoginComponent";
import {ProfileComponent} from "./profile/ProfileComponent";
import {MyRidesComponent} from "./profile/myrides_section/MyRidesComponent";
import {MyRidesUpdateComponent} from "./profile/myrides_section/myrides_update/MyRidesUpdateComponent";


export const ROUTES: Routes = [
  { path: '',      component: HomeComponent },
  { path: 'home',  component: HomeComponent },
  { path: 'register',  component: RegistrationComponent },
  { path: 'login',  component: LoginComponent },
  { path: 'createRide',  component: CreateRideComponent },
  { path: 'searchRide',  component: SearchRideComponent },
  { path: 'profile',  component: ProfileComponent },
  { path: 'profile/myRides',  component: MyRidesComponent },
  { path: 'profile/myRides/myRidesUpdate',  component: MyRidesUpdateComponent },
  { path: 'about', component: AboutComponent },
  { path: '**',    component: NoContentComponent },
];
