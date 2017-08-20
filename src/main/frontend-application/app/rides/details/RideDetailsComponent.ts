import {Component, OnInit} from "@angular/core";
import {Ride} from "../create/Ride";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "@angular/common";
import {SecurityStatus} from "../../security/SecurityStatus";
import {RideJoin} from "./RideJoin";
import {RideService} from "../../services/RideService";

@Component({
    selector: 'ride-details',
    providers: [],
    styleUrls: ['./RideDetailsComponent.css'],
    templateUrl: './RideDetailsComponent.html'
})
export class RideDetailsComponent implements OnInit {

    ride: Ride;
    rideJoins: RideJoin[];

    constructor(private rideService: RideService,
                private route: ActivatedRoute,
                private router: Router,
                private location: Location,
                private securityStatus: SecurityStatus) {
    }

    ngOnInit(): void {
        this.route.data
            .subscribe((data: { ride: Ride, rideJoins: RideJoin[] }) => {
                this.ride = data.ride;
                this.rideJoins = data.rideJoins;
            });
    }

    goToUpdate() {
        this.router.navigate(['/rides', this.ride.id, 'update']);
    }

    joinRide() {
        this.rideService.joinRide(this.ride).then(rideJoin => {
            this.rideJoins.push(rideJoin);
            // TODO show confirmation message
        }).catch(reason => {
            console.error("failed to join ride, TODO message");
        });
    }

    isMyRide(): boolean {
        return this.securityStatus.username === this.ride.providerUsername;
    }
}
