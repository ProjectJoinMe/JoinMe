import {Component} from "@angular/core";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {SearchRequest} from "./SearchRequest";
import {RideService} from "../../services/RideService";
import {Ride} from "../model/Ride";
import {LatLng} from "../model/LatLng";
import {MessageService} from "../../message_service/MessageService";

/**
 * Created by Nicole August 2017.
 */

@Component({
    selector: 'searchRide',
    providers: [],
    styleUrls: ['./SearchRideComponent.css'],
    templateUrl: './SearchRideComponent.html'

})
export class SearchRideComponent {

    public searchForm: FormGroup;
    public currentDate: Date = new Date();
    public submitted: boolean = false;
    public submitDisabled: boolean = false;
    public rides: Ride[];
    private startLocation: LatLng = null;
    private destinationLocation: LatLng = null;
    private onStartPlaceChange: (place: any, isFullPlace: boolean) => void;
    private onDestinationPlaceChange: (place: any, isFullPlace: boolean) => void;

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private rideService: RideService,
                private messageService: MessageService) {
        this.onStartPlaceChange = (place: any, isFullPlace: boolean) => {
            if (isFullPlace) {
                let location = place.geometry.location;
                this.startLocation = this.mapsLatLngToJoinMeLatLng(location);
            } else {
                this.startLocation = null;
            }
        };
        this.onDestinationPlaceChange = (place: any, isFullPlace: boolean) => {
            if (isFullPlace) {
                let location = place.geometry.location;
                this.destinationLocation = this.mapsLatLngToJoinMeLatLng(location);
            } else {
                this.destinationLocation = null;
            }
        }
    }

    ngOnInit() {
        this.searchForm = this.formBuilder.group({
            start: ["",],
            startPlaceId: ["", [this.requiredWhenStartSearchTextNotEmpty]],
            destination: ["",],
            destinationPlaceId: ["", [this.requiredWhenDestinationSearchTextNotEmpty]],
            date: ["",],
        });
    }

    public search() {
        this.submitted = true;
        if (this.searchForm.valid) {
            this.submitDisabled = true;
            let date = new Date(this.searchForm.get("date").value);
            this.rideService.searchRides(this.startLocation, this.destinationLocation, date)
                .then(rides => {
                    this.submitDisabled = false;
                    this.rides = rides;
                })
                .catch(reason => {
                    this.submitDisabled = false;
                    this.messageService.setMessage("Es konnten keine Fahrten gefunden werden.", "failure");
                    // TODO handle all unhandled errors generally by showing some message bar
                });
        }
    }

    goToDetails(ride: Ride) {
        this.router.navigate(['/rides', ride.id]);
    }

    private mapsLatLngToJoinMeLatLng(location: any): LatLng {
        return {
            lat: location.lat(),
            lng: location.lng()
        };
    }

    private requiredWhenStartSearchTextNotEmpty(control: AbstractControl): any {
        let startControl = control.root.get("start");
        return (startControl && startControl.value) ? Validators.required(control) : null;
    }

    private requiredWhenDestinationSearchTextNotEmpty(control: AbstractControl): any {
        let destinationControl = control.root.get("destination");
        return (destinationControl && destinationControl.value) ? Validators.required(control) : null;
    }

}
