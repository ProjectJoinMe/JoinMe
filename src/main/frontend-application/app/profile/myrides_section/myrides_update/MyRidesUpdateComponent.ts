import {Component} from "@angular/core";
import {Ride} from "../../../rides/create_ride/Ride";
import {Http} from "@angular/http";

@Component({
    selector: 'myRidesUpdate',
    providers: [],
    styleUrls: ['./MyRidesUpdateComponent.css'],
    templateUrl: './MyRidesUpdateComponent.html'
})
export class MyRidesUpdateComponent {

    rides: Ride[];

    constructor(private http: Http) {
        this.getRides();
    }

    getRides(): void {
        this.http.get("/api/rides/myRides")
            .subscribe(
                data => {
                    this.rides = data.json();
                },
                error => {
                    // TODO error handling
                });
    }

}
