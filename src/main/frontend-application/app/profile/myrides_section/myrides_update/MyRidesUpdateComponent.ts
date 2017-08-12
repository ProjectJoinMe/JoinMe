import {Component, OnInit} from "@angular/core";
import {Ride} from "../../../rides/create_ride/Ride";
import {Http} from "@angular/http";
import {ActivatedRoute, Params} from "@angular/router";
import {Validators, FormGroup, FormBuilder, FormControl, FormArray} from "@angular/forms";
import {Router} from "@angular/router";
import {DatePipe} from '@angular/common';
import {TimezonifyDatePipe} from "../../../util/time/TimezonifyDatePipe";

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
                private router: Router,
                private datePipe: DatePipe,
                private timezonifyDatePipe: TimezonifyDatePipe) {
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
                id: this.ride.id,
                start: <string> this.updateRideForm.get("start").value,
                destination: <string> this.updateRideForm.get("destination").value,
                departureDateTime: new Date(departureDate.getFullYear(), departureDate.getMonth(), departureDate.getDate(), departureHour, departureMinute, 0, 0),
                returnDepartureDateTime: this.getReturnRide() ? new Date(returnDepartureDate.getFullYear(), returnDepartureDate.getMonth(), returnDepartureDate.getDate(), returnDepartureHour, returnDepartureMinute, 0, 0) : null,
                maxPassengers: <number> this.updateRideForm.get("maxPassengers").value,
                notes: this.updateRideForm.get("notes").value
            };
            console.info(rideData);
            this.http.post("api/rides/updateRide", rideData)
                .subscribe(
                    data => {
                        this.submitDisabled = false;
                        this.router.navigate(['/profile/myRides/' + this.ride.id]);
                        // TODO success -> show confirmation message
                    },
                    error => {
                        this.submitDisabled = false;
                        console.error("failed to create ride, TODO");
                    });
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
                    var departureDateTime = this.timezonifyDatePipe.transform(this.ride.departureDateTime);
                    var returnDepartureDateTime = this.timezonifyDatePipe.transform(this.ride.returnDepartureDateTime);
                    this.updateRideForm.setValue({
                        start: this.ride.start,
                        destination: this.ride.destination,
                        departureDate: this.datePipe.transform(departureDateTime, 'yyyy-MM-dd'),
                        departureHour: this.datePipe.transform(departureDateTime, 'HH'),
                        departureMinute: this.datePipe.transform(departureDateTime, 'mm'),
                        returnRide: returnDepartureDateTime !== null,
                        returnDepartureDate: this.getDateComponent(returnDepartureDateTime, 'yyyy-MM-dd'),
                        returnDepartureHour: this.getDateComponent(returnDepartureDateTime, 'HH'),
                        returnDepartureMinute: this.getDateComponent(returnDepartureDateTime, 'mm'),
                        maxPassengers: this.ride.maxPassengers,
                        notes: this.ride.notes
                    });
                },
                error => {
                    // TODO error handling
                });
    }

    private getDateComponent(date: Date, format: string) {
        return date !== null
            ? this.datePipe.transform(date, format)
            : null;
    }

    goToDetails(ride: Ride) {
        this.router.navigate(['/profile/myRides', ride.id]);
    }

}
