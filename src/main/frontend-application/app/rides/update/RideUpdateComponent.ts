import {Component, OnInit} from "@angular/core";
import {Ride} from "../create/Ride";
import {Http} from "@angular/http";
import {ActivatedRoute, Params} from "@angular/router";
import {Validators, FormGroup, FormBuilder, FormControl, FormArray} from "@angular/forms";
import {Router} from "@angular/router";
import {DatePipe} from '@angular/common';
import {TimezonifyDatePipe} from "../../util/time/TimezonifyDatePipe";

@Component({
    selector: 'myRidesUpdate',
    providers: [],
    styleUrls: ['./RideUpdateComponent.css'],
    templateUrl: './RideUpdateComponent.html'
})
export class RideUpdateComponent implements OnInit {

    public rideForm: FormGroup;
    public currentDate: Date = new Date();
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

        this.rideForm = this.formBuilder.group({
            start: ["", [Validators.required]],
            startPlaceId: ["", [Validators.required]],
            destination: ["", [Validators.required]],
            destinationPlaceId: ["", [Validators.required]],
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
        this.submitted = true;
        if (this.rideForm.valid) {
            console.info("updating ride");
            this.submitDisabled = true;
            let departureDate = new Date(this.rideForm.get("departureDate").value);
            let departureHour = <number> this.rideForm.get("departureHour").value;
            let departureMinute = <number> this.rideForm.get("departureMinute").value;
            let returnDepartureDate = new Date(this.rideForm.get("returnDepartureDate").value);
            let returnDepartureHour = <number> this.rideForm.get("returnDepartureHour").value;
            let returnDepartureMinute = <number> this.rideForm.get("returnDepartureMinute").value;
            let rideData: Ride = {
                id: this.ride.id,
                start: <string> this.rideForm.get("start").value,
                startPlaceId: <string> this.rideForm.get("startPlaceId").value,
                destination: <string> this.rideForm.get("destination").value,
                destinationPlaceId: <string> this.rideForm.get("destinationPlaceId").value,
                departureDateTime: new Date(departureDate.getFullYear(), departureDate.getMonth(), departureDate.getDate(), departureHour, departureMinute, 0, 0),
                returnDepartureDateTime: this.getReturnRide() ? new Date(returnDepartureDate.getFullYear(), returnDepartureDate.getMonth(), returnDepartureDate.getDate(), returnDepartureHour, returnDepartureMinute, 0, 0) : null,
                maxPassengers: <number> this.rideForm.get("maxPassengers").value,
                notes: this.rideForm.get("notes").value
            };
            console.info(rideData);
            this.http.post("api/rides/updateRide", rideData)
                .subscribe(
                    data => {
                        this.submitDisabled = false;
                        this.router.navigate(['/rides', this.ride.id]);
                        // TODO success -> show confirmation message
                    },
                    error => {
                        this.submitDisabled = false;
                        console.error("failed to update ride, TODO");
                    });
        }
    }

    public getReturnRide() {
        return this.rideForm.get("returnRide").value;
    }

    getRide(id: number): void {
        this.http.get("/api/rides/" + id)
            .subscribe(
                data => {
                    this.ride = data.json();
                    var departureDateTime = this.timezonifyDatePipe.transform(this.ride.departureDateTime);
                    var returnDepartureDateTime = this.timezonifyDatePipe.transform(this.ride.returnDepartureDateTime);
                    this.rideForm.setValue({
                        start: this.ride.start,
                        startPlaceId: this.ride.startPlaceId,
                        destination: this.ride.destination,
                        destinationPlaceId: this.ride.destinationPlaceId,
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
        this.router.navigate(['/rides', ride.id]);
    }

}
