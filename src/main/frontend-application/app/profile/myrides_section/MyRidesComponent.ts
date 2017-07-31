import {Component} from "@angular/core";
import {Ride} from "../../rides/create_ride/Ride";
import {Http} from "@angular/http";
import {Router} from "@angular/router";

@Component({
    selector: 'myRides',
    providers: [],
    styleUrls: ['./MyRidesComponent.css'],
    templateUrl: './MyRidesComponent.html'
})
export class MyRidesComponent {

    rides: Ride[];

    constructor(private http: Http,
                private router: Router) {
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

    goToDetails(ride: Ride) {
        this.router.navigate(['/profile/myRides', ride.id]);
    }

}
