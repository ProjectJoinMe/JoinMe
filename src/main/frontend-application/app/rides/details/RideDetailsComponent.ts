import {Component, OnInit} from "@angular/core";
import {Ride} from "../create/Ride";
import {Http} from "@angular/http";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from "@angular/common";
import {SecurityStatus} from "../../security/SecurityStatus";
import {RideJoin} from "./RideJoin";

@Component({
    selector: 'ride-details',
    providers: [],
    styleUrls: ['./RideDetailsComponent.css'],
    templateUrl: './RideDetailsComponent.html'
})
export class RideDetailsComponent implements OnInit {

    ride: Ride;
    rideJoins: RideJoin[];

    constructor(private http: Http,
                private route: ActivatedRoute,
                private router: Router,
                private location: Location,
                private securityStatus: SecurityStatus) {
    }

    ngOnInit(): void {
        // TODO change after angular upgrade according to routing tutorial
        this.route.params
            .subscribe((params: Params) => {
                let id = parseInt(params['id']);
                this.getRide(id);
                this.getRideJoins(id);
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

    getRideJoins(rideId: number): void {
        this.http.get("/api/rides/" + rideId + "/joins")
            .subscribe(
                data => {
                    this.rideJoins = data.json();
                },
                error => {
                    // TODO error handling
                });
    }

    goToUpdate() {
        this.router.navigate(['/rides', this.ride.id, 'update']);
    }

    joinRide() {
        this.http.post("api/rides/" + this.ride.id + "/join", null)
            .subscribe(
                data => {
                    // TODO success -> show confirmation message
                },
                error => {
                    console.error("failed to join ride, TODO");
                });
    }

    isMyRide(): boolean {
        return this.securityStatus.username === this.ride.providerUsername;
    }
}
