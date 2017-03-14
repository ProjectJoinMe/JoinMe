import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home';
import { CreateRideComponent } from './rides/create_ride';
import { AboutComponent } from './about';
import { NoContentComponent } from './no-content';

import { DataResolver } from './AppResolver';
import {RegistrationComponent} from "./registration/RegistrationComponent";
import {LoginComponent} from "./login/LoginComponent";


export const ROUTES: Routes = [
  { path: '',      component: HomeComponent },
  { path: 'home',  component: HomeComponent },
  { path: 'register',  component: RegistrationComponent },
  { path: 'login',  component: LoginComponent },
  { path: 'createRide',  component: CreateRideComponent },
  { path: 'about', component: AboutComponent },
  { path: '**',    component: NoContentComponent },
];
