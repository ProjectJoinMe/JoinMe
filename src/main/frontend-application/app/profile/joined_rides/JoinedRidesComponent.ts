import {Component} from "@angular/core";
import {Ride} from "../../rides/create/Ride";
import {Http} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'joinedRides',
    providers: [],
    styleUrls: ['./JoinedRidesComponent.css'],
    templateUrl: './JoinedRidesComponent.html'
})
export class JoinedRidesComponent {

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
