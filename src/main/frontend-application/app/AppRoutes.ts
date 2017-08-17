import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home';
import { CreateRideComponent } from './rides/create';
import { SearchRideComponent } from './rides/search';
import { AboutComponent } from './about';
import { NoContentComponent } from './no-content';

import { DataResolver } from './AppResolver';
import {RegistrationComponent} from "./registration/RegistrationComponent";
import {LoginComponent} from "./login/LoginComponent";
import {ProfileComponent} from "./profile/ProfileComponent";
import {MyRidesComponent} from "./profile/rides/MyRidesComponent";
import {RideUpdateComponent} from "./rides/update/RideUpdateComponent";
import {RideDetailsComponent} from "./rides/details/RideDetailsComponent";


export const ROUTES: Routes = [
  { path: '',      component: HomeComponent },
  { path: 'home',  component: HomeComponent },
  { path: 'register',  component: RegistrationComponent },
  { path: 'login',  component: LoginComponent },
  { path: 'createRide',  component: CreateRideComponent },
  { path: 'searchRide',  component: SearchRideComponent },
  { path: 'profile/:username',  component: ProfileComponent },
  { path: 'profile/myRides',  component: MyRidesComponent },
  { path: 'rides/:id',  component: RideDetailsComponent },
  { path: 'rides/:id/update',  component: RideUpdateComponent },
  { path: 'about', component: AboutComponent },
  { path: '**',    component: NoContentComponent },
];
