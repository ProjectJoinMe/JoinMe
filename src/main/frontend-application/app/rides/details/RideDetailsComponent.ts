import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "@angular/common";
import {SecurityStatus} from "../../security/SecurityStatus";
import {RideService} from "../../services/RideService";
import {Ride} from "../model/Ride";
import {RideJoin} from "../model/RideJoin";

@Component({
    selector: 'ride-details',
    providers: [],
    styleUrls: ['./RideDetailsComponent.css'],
    templateUrl: './RideDetailsComponent.html'
})
export class RideDetailsComponent implements OnInit {

    ride: Ride;
    rideJoins: RideJoin[];
    joined: boolean;

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
                let rideJoin = this.rideJoins.find(rideJoin => rideJoin.userProfileDto.username === this.securityStatus.username);
                this.joined = rideJoin !== undefined;
            });
    }


    goToUpdate() {
        this.router.navigate(['/rides', this.ride.id, 'update']);
    }

    joinRide() {
        this.rideService.joinRide(this.ride).then(rideJoin => {
            this.rideJoins.push(rideJoin);
            this.joined = true;
            this.ride.freeSeats--;
            // TODO show confirmation message
        }).catch(reason => {
            console.error("failed to join ride, TODO message");
        });
    }

    unjoinRide() {
        this.rideService.unjoinRide(this.ride).then(nothing => {
            let rideJoin = this.rideJoins.find(rideJoin => rideJoin.userProfileDto.username === this.securityStatus.username);
            this.rideJoins.splice(this.rideJoins.indexOf(rideJoin), 1);
            this.joined = false;
            this.ride.freeSeats++;
            // TODO show confirmation message
        }).catch(reason => {
            console.error("failed to unjoin ride, TODO message");
        });
    }

    deleteRide() {
        //TODO send information message to all users that joined that ride
        this.rideService.deleteRide(this.ride).then(nothing => {
            this.router.navigate(['/profile', this.securityStatus.username, 'rides']);
            // TODO show confirmation message
        }).catch(reason => {
            console.error("failed to unjoin ride, TODO message");
        });
    }

    isMyRide(): boolean {
        return this.securityStatus.username === this.ride.providerUsername;
    }
}
