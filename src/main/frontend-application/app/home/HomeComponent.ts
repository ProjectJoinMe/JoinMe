import { Component } from '@angular/core';

import { AppState } from '../AppService';
import {Router} from "@angular/router";

@Component({
  selector: 'home',  // <home></home>
  providers: [
  ],
  styleUrls: [ './HomeComponent.css' ],
  templateUrl: './HomeComponent.html'
})
export class HomeComponent {
  constructor(private router: Router) {
  }

  goToRegistration() {
    this.router.navigate(['/register']);
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
