import {Component, OnInit} from "@angular/core";
import {Ride} from "../../rides/create_ride/Ride";
import {Http} from "@angular/http";
import {Input} from "@angular/core/src/metadata/directives";
import {ActivatedRoute, Params} from "@angular/router";
import {Router} from "@angular/router";

@Component({
    selector: 'ride-details',
    providers: [],
    styleUrls: ['./RideDetailsComponent.css'],
    templateUrl: './RideDetailsComponent.html'
})
export class RideDetailsComponent implements OnInit {

    ride: Ride;

    constructor(private http: Http,
                private route: ActivatedRoute,
                private router: Router,) {
    }

    ngOnInit(): void {
        // TODO change after angular upgrade according to routing tutorial
        this.route.params
            .subscribe((params: Params) => {
                this.getRide(parseInt(params['id']));
            });
    }

    getRide(id: number): void {
        this.http.get("/api/rides/" + id)
            .subscribe(
                data => {
                    this.ride = data.json();
                },
                error => {
                    // TODO error handling
                });
    }

    goToUpdate(ride: Ride) {
        this.router.navigate(['/profile/myRides/myRidesUpdate', ride.id]);
    }

}
