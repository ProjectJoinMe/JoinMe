import {Component, OnInit} from "@angular/core";
import {Ride} from "../../../rides/create_ride/Ride";
import {Http} from "@angular/http";
import {ActivatedRoute, Params} from "@angular/router";
import {Validators, FormGroup, FormBuilder, FormControl, FormArray} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
    selector: 'myRidesUpdate',
    providers: [],
    styleUrls: ['./MyRidesUpdateComponent.css'],
    templateUrl: './MyRidesUpdateComponent.html'
})
export class MyRidesUpdateComponent implements OnInit {

    public updateRideForm: FormGroup;
    public submitted: boolean = false;
    public submitDisabled: boolean = false;
    private ride: Ride;

    constructor(private http: Http,
                private formBuilder: FormBuilder,
                private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit(): void {
        // TODO change after angular upgrade according to routing tutorial
        this.route.params
            .subscribe((params: Params) => {
                this.getRide(parseInt(params['id']));
            });

        this.updateRideForm = this.formBuilder.group({
            start: ["", [Validators.required]],
            destination: ["", [Validators.required]],
            departureDate: ["", [Validators.required]],
            departureHour: ["", Validators.required],
            departureMinute: ["", Validators.required],
            returnRide: [""],
            returnDepartureDate: [""],
            returnDepartureHour: [""],
            returnDepartureMinute: [""],
            maxPassengers: ["", Validators.required],
            notes: [""],
        });
    }

    public updateRide() {
        console.info("updating ride");
        this.submitted = true;
        if (this.updateRideForm.valid) {
            this.submitDisabled = true;
            let departureDate = new Date(this.updateRideForm.get("departureDate").value);
            let departureHour = <number> this.updateRideForm.get("departureHour").value;
            let departureMinute = <number> this.updateRideForm.get("departureMinute").value;
            let returnDepartureDate = new Date(this.updateRideForm.get("returnDepartureDate").value);
            let returnDepartureHour = <number> this.updateRideForm.get("returnDepartureHour").value;
            let returnDepartureMinute = <number> this.updateRideForm.get("returnDepartureMinute").value;
            let rideData: Ride = {
                start: <string> this.updateRideForm.get("start").value,
                destination: <string> this.updateRideForm.get("destination").value,
                departureDateTime: new Date(departureDate.getFullYear(), departureDate.getMonth(), departureDate.getMinutes(), departureHour, departureMinute, 0, 0),
                returnDepartureDateTime: new Date(returnDepartureDate.getFullYear(), returnDepartureDate.getMonth(), returnDepartureDate.getMinutes(), returnDepartureHour, returnDepartureMinute, 0, 0),
                maxPassengers: <number> this.updateRideForm.get("maxPassengers").value,
                notes: this.updateRideForm.get("notes").value
            };
            console.info(rideData);
            /**
            this.http.post("api/rides/updateRide", rideData)
                .subscribe(
                    data => {
                        this.submitDisabled = false;
                        this.router.navigate(['/profile']);
                        // TODO success -> show confirmation message and move to login screen
                    },
                    error => {
                        this.submitDisabled = false;
                        console.error("failed to create ride, TODO");
                    });
             **/
        }
    }

    public getReturnRide() {
        return this.updateRideForm.get("returnRide").value;
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

    goToDetails(ride: Ride) {
        this.router.navigate(['/profile/myRides', ride.id]);
    }

}
