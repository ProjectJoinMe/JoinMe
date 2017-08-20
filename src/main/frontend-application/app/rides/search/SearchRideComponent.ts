import {Component} from "@angular/core";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {SearchRequest} from "./SearchRequest";
import {Ride} from "../create/Ride";
import {RideService} from "../../services/RideService";

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

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private rideService: RideService) {
    }

    public search() {
        this.submitted = true;
        if (this.searchForm.valid) {
            this.submitDisabled = true;
            let searchRequest = {
                start: <string> this.searchForm.get("start").value,
                destination: <string> this.searchForm.get("destination").value,
                date: new Date(this.searchForm.get("date").value)
            };
            this.rideService.searchRides(searchRequest.start || null, searchRequest.destination || null, searchRequest.date)
                .then(rides => {
                    this.submitDisabled = false;
                    this.rides = rides;
                })
                .catch(reason => {
                    this.submitDisabled = false;
                    console.error("failed to search rides, TODO");
                    // TODO handle all unhandled errors generally by showing some message bar
                });
        }
    }

    ngOnInit() {
        this.searchForm = this.formBuilder.group({
            start: ["",],
            destination: ["",],
            date: ["",],
        });
    }

    goToDetails(ride: Ride) {
        this.router.navigate(['/rides', ride.id]);
    }

}
