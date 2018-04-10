import {Component} from "@angular/core";
import {Http} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {Ride} from "../../rides/model/Ride";

/**
 * Created by Nicole August 2017.
 */

@Component({
    selector: 'myRides',
    providers: [],
    styleUrls: ['./MyRidesComponent.css'],
    templateUrl: './MyRidesComponent.html'
})
export class MyRidesComponent {

    rides: Ride[];

    constructor(private http: Http,
                private router: Router,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.data
            .subscribe((data: { rides: Ride[] }) => {
                this.rides = data.rides;
            });
    }

    goToDetails(ride: Ride) {
        this.router.navigate(['/rides', ride.id]);
    }

}
