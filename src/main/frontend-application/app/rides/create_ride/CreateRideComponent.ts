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

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private http: Http) {
    }

    public createRide() {
        this.submitted = true;
        if (this.createRideForm.valid) {

        }
    }

    ngOnInit() {
        this.createRideForm = this.formBuilder.group({
            departurePlace: ["", [Validators.required]],
            arrivalPlace: ["", [Validators.required]],
            departureDateTo: ["", [Validators.required]],
            departureTimeHourTo: ["", Validators.required],
            departureTimeMinuteTo: ["", Validators.required],
            departureDateReturn: [""],
            departureTimeHourReturn: [""],
            departureTimeMinuteReturn: [""],
        });
    }

}
