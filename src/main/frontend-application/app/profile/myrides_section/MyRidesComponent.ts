import { Component } from '@angular/core';
import {Ride} from "../../rides/create_ride/Ride";

@Component({
  selector: 'myRides',
  providers: [],
  styleUrls: ['./MyRidesComponent.css'],
  templateUrl: './MyRidesComponent.html'
})
export class MyRidesComponent {

  rides: Ride[];



}
