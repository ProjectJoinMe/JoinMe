import { Component } from '@angular/core';

import { AppState } from '../AppService';
import {Router} from "@angular/router";
import {MessageService} from "../message_service/MessageService";

@Component({
  selector: 'home',  // <home></home>
  providers: [
  ],
  styleUrls: [ './HomeComponent.css' ],
  templateUrl: './HomeComponent.html'
})
export class HomeComponent {
  constructor(private router: Router,
              private messageService: MessageService) {
  }

  goToRegistration() {
    this.router.navigate(['/register']);
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

  goToCreateRide(){
  this.router.navigate(['/createRide']);
}
}
