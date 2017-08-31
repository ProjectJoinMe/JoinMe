import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "@angular/common";
import {SecurityStatus} from "../../security/SecurityStatus";
import {RideService} from "../../services/RideService";
import {Ride} from "../model/Ride";
import {RideJoin} from "../model/RideJoin";
import {MessageService} from "../../message_service/MessageService";

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
    rideFull: boolean;

    constructor(private rideService: RideService,
                private route: ActivatedRoute,
                private router: Router,
                private location: Location,
                private securityStatus: SecurityStatus,
                private messageService: MessageService) {
    }

    ngOnInit(): void {
        this.route.data
            .subscribe((data: { ride: Ride, rideJoins: RideJoin[] }) => {
                this.ride = data.ride;
                this.rideJoins = data.rideJoins;
                let rideJoin = this.rideJoins.find(rideJoin => rideJoin.userProfileDto.username === this.securityStatus.username);
                this.joined = rideJoin !== undefined;
                this.rideFull = (this.ride.maxPassengers - this.rideJoins.length) === 0;
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
            this.rideFull = (this.ride.maxPassengers - this.rideJoins.length) === 0;
            this.messageService.setMessage("Fahrt erfolgreich beigetreten.", "success");
        }).catch(reason => {
            this.messageService.setMessage("Der Fahrt konnte nicht beigetreten werden.", "failure");
        });
    }

    unjoinRide() {
        this.rideService.unjoinRide(this.ride).then(nothing => {
            let rideJoin = this.rideJoins.find(rideJoin => rideJoin.userProfileDto.username === this.securityStatus.username);
            this.rideJoins.splice(this.rideJoins.indexOf(rideJoin), 1);
            this.joined = false;
            this.ride.freeSeats++;
            this.rideFull = (this.ride.maxPassengers - this.rideJoins.length) === 0;
            this.messageService.setMessage("Teilnahme erfolgreich zurückgezogen", "success");
        }).catch(reason => {
            this.messageService.setMessage("Die Teilnahme konnte nicht zurückgezogen werden.", "failure");
        });
    }

    deleteRide() {
        //TODO send information message to all users that joined that ride
        this.rideService.deleteRide(this.ride).then(nothing => {
            this.router.navigate(['/profile', this.securityStatus.username, 'rides']);
            this.messageService.setMessage("Ihre Fahrt wurde erfolgreich gelöscht.", "success");
        }).catch(reason => {
            this.messageService.setMessage("Fahrt konnte nicht gelöscht werden.", "failure");
        });
    }

    isMyRide(): boolean {
        return this.securityStatus.username === this.ride.providerUsername;
    }
}
