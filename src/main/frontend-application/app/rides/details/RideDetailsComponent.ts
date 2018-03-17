import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "@angular/common";
import {SecurityStatus} from "../../security/SecurityStatus";
import {RideService} from "../../services/RideService";
import {Ride} from "../model/Ride";
import {RideJoin} from "../model/RideJoin";
import {MessageService} from "../../message_service/MessageService";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Rating} from "../../ratings/Rating";
import {RatingService} from "../../services/RatingService";
import {RideJoinRating} from "../model/RideJoinRating";

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
    rideJoin: RideJoin;
    ratingForm: FormGroup;
    submitted: boolean;
    submitDisabled: boolean;
    rating: Rating;
    rated: boolean;
    today: Date = new Date();
    rideDepDate: Date;
    public ratings: Rating [];

    constructor(private rideService: RideService,
                private ratingService: RatingService,
                private route: ActivatedRoute,
                private router: Router,
                private formBuilder: FormBuilder,
                private location: Location,
                private securityStatus: SecurityStatus,
                private messageService: MessageService) {
    }

    ngOnInit(): void {
        this.route.data
            .subscribe((data: { ride: Ride, rideJoins: RideJoin[] }) => {
                this.ride = data.ride;
                this.rideJoins = data.rideJoins;
                this.rideJoin = this.rideJoins.find(rideJoin => rideJoin.userProfileDto.username === this.securityStatus.username);
                if (this.rideJoin) {
                    this.joined = true; //Truthy check
                }
                this.rideFull = (this.ride.maxPassengers - this.rideJoins.length) === 0;

            });
        this.rated = false;
        if (this.rideJoin !== undefined) {
            this.rating = this.rideJoin.ratingDto;
            if (this.rating) {
                this.rated = true; //Truthy check
            }
        }
        this.ratingForm = this.formBuilder.group({
            ratingValue: [5, [Validators.required,]],
            ratingComment: ["", []]
        });
        this.rideDepDate = new Date(this.ride.departureDateTime);

        if (this.rideDepDate < this.today) {

            this.ratingService.getRatingsForRide(this.ride).then(ratings => {
                this.ratings = ratings;
            }).catch(reason => {
                this.messageService.setMessage("Fehler beim Laden der Bewertungen", "failure");
            });
        }
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

    rate() {
        this.submitted = true;
        if (this.ratingForm.valid && this.rated === false && this.joined !== false) {
            console.info("rating...");
            this.submitDisabled = true;

            let ratingValue = <number> this.ratingForm.get("ratingValue").value;
            let ratingComment = <string> this.ratingForm.get("ratingComment").value;

            let rating: Rating = <Rating>{
                rating: ratingValue,
                comment: ratingComment,
                creationDateTime: new Date()
            };

            console.info(this.rideJoin);

            this.ratingService.createRatingForRideJoin(rating, this.rideJoin).then(rating => {
                this.messageService.setMessage("Bewertung erfolgreich abgegeben.", "success");
                this.rating = rating;
                this.rated = true;
                location.reload(); // to reload the page after rating
            }).catch(reason => {
                this.submitDisabled = false;
                this.messageService.setMessage("Bewertung konnte nicht abgegeben werden.", "failure");
                console.info(reason.toString());
            });
        } else {
            this.messageService.setMessage("Bewertung ungültig.", "failure");
        }
    }

    getWeekDayNames(): string {
        const allWeekDayNames: string[] = ["Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"];
        return this.ride.periodicWeekDays.map(value => allWeekDayNames[value - 1])
            .join(", ");
    }

    goToProviderProfile() {
        this.router.navigate(['/profile', this.ride.providerUsername]);
    }
}
