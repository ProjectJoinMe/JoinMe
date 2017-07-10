import {Component, OnInit} from "@angular/core";
import {RideData} from "./RideData";
import {Validators, FormGroup, FormBuilder, FormControl, AbstractControl} from "@angular/forms";
import {Http, URLSearchParams} from "@angular/http";
import {MailValidator} from "../../../validators/MailValidator";
import cloneWith = require("lodash/cloneWith");
import {Router} from "@angular/router";
import {Observable} from "rxjs";

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
            let rideData: RideData = {
                start: <string> this.createRideForm.get("start").value,
                destination: <string> this.createRideForm.get("destination").value,
                departureDate: new Date(this.createRideForm.get("departureDay").value),
                departureHour: <number> this.createRideForm.get("departureHour").value,
                departureMinute: <number> this.createRideForm.get("departureMinute").value,
                returnRide: <boolean> this.createRideForm.get("returnRide").value,
                returnDepartureDate: new Date(this.createRideForm.get("returnDepartureDate").value),
                returnDepartureHour: <number> this.createRideForm.get("returnDepartureHour").value,
                returnDepartureMinute: <number> this.createRideForm.get("returnDepartureMinute").value,
                periodic: <boolean> this.createRideForm.get("periodic").value,
                freeSeats: <number> this.createRideForm.get("freeSeats").value,
                periodicDays: null
            };
            console.info(rideData.toString());
        }
    }

    ngOnInit(){
        this.createRideForm = this.formBuilder.group({
            start: ["", [Validators.required]],
            destination: ["", [Validators.required]],
            departureDate: ["", [Validators.required]],
            departureHour: ["", Validators.required],
            departureMinute: ["", Validators.required],
            returnRide: ["", Validators.required],
            returnDepartureDate: [""],
            returnDepartureHour: [""],
            returnDepartureMinute: [""],
            periodic: ["", Validators.required],
            freeSeats: ["" , Validators.required],
        });
    }

}
