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
        this.submitted = true;
        if (this.createRideForm.valid) {
            // let rideData: RideData = {
            //     start: <string> this.createRideForm.get("").value,
            // };
        }
    }

    ngOnInit(){
        this.createRideForm = this.formBuilder.group({
            start: ["", [Validators.required]],
            destination: ["", [Validators.required]],
            departureDate: ["", [Validators.required]],
            departureHour: ["", Validators.required],
            departureMinute: ["", Validators.required],
            returnDepartureDate: [""],
            returnDepartureHour: [""],
            returnDepartureMinute: [""],
            freeSeats: ["" , Validators.required],
        });
    }

}
