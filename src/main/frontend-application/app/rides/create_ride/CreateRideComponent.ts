import {Component, OnInit} from "@angular/core";
import {RideData} from "./RideData";
import {Validators, FormGroup, FormBuilder, FormControl, FormArray} from "@angular/forms";
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {WeekDay} from "../WeekDay";
import cloneWith = require("lodash/cloneWith");
import {debugOutputAstAsTypeScript} from "@angular/compiler";

@Component({
    selector: 'createRide',
    providers: [],
    styleUrls: ['./CreateRideComponent.css'],
    templateUrl: './CreateRideComponent.html'
})
export class CreateRideComponent implements OnInit {

    public createRideForm: FormGroup;
    public submitted: boolean = false;
    public submitDisabled: boolean = false;

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private http: Http) {
    }

    public createRide() {
        console.info("creating ride");
        this.submitted = true;
        if (this.createRideForm.valid) {
            this.submitDisabled = true;
            this.createRideForm.get("periodicDays");
            let periodicWeekDays: WeekDay[] = [];
            if (<boolean> this.createRideForm.get("periodic").value) {
                let periodicDaysControl = <FormArray> this.createRideForm.get("periodicDays");
                let weekDayControls: FormControl[] = <FormControl[]> periodicDaysControl.controls;
                periodicWeekDays = weekDayControls.map((weekDayControl, index) => {
                    if (weekDayControl.value) {
                        return <WeekDay>(index + 1);
                    } else {
                        return null;
                    }
                }).filter((index) => index !== null);
            }
            let departureDate = new Date(this.createRideForm.get("departureDate").value);
            let departureHour = <number> this.createRideForm.get("departureHour").value;
            let departureMinute = <number> this.createRideForm.get("departureMinute").value;
            let returnDepartureDate = new Date(this.createRideForm.get("returnDepartureDate").value);
            let returnDepartureHour = <number> this.createRideForm.get("returnDepartureHour").value;
            let returnDepartureMinute = <number> this.createRideForm.get("returnDepartureMinute").value;
            let rideData: RideData = {
                start: <string> this.createRideForm.get("start").value,
                destination: <string> this.createRideForm.get("destination").value,
                departureDateTime: new Date(departureDate.getFullYear(), departureDate.getMonth(), departureDate.getMinutes(), departureHour, departureMinute, 0, 0),
                returnRide: <boolean> this.createRideForm.get("returnRide").value,
                returnDepartureDateTime: new Date(returnDepartureDate.getFullYear(), returnDepartureDate.getMonth(), returnDepartureDate.getMinutes(), returnDepartureHour, returnDepartureMinute, 0, 0),
                maxPassengers: <number> this.createRideForm.get("maxPassengers").value,
                notes: this.createRideForm.get("notes").value
            };
            console.info(rideData);
            this.http.post("api/rides/createRide", rideData)
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
        }
    }

    public getPeriodic() {
        return this.createRideForm.get("periodic").value;
    }

    public getReturnRide() {
        return this.createRideForm.get("returnRide").value;
    }

    ngOnInit() {
        this.createRideForm = this.formBuilder.group({
            start: ["", [Validators.required]],
            destination: ["", [Validators.required]],
            departureDate: ["", [Validators.required]],
            departureHour: ["", Validators.required],
            departureMinute: ["", Validators.required],
            returnRide: [""],
            returnDepartureDate: [""],
            returnDepartureHour: [""],
            returnDepartureMinute: [""],
            periodic: [""],
            maxPassengers: ["", Validators.required],
            notes: [""],
            periodicDays: this.formBuilder.array([false, false, false, false, false, false, false])
        });
    }
}
