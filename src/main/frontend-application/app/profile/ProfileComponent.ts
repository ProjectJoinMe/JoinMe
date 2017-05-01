import {Component} from '@angular/core';
import cloneWith = require("lodash/cloneWith");
import {Validators, FormControl, FormGroup, ReactiveFormsModule, FormBuilder} from "@angular/forms";
import {Http, Response, Headers, URLSearchParams} from "@angular/http";
import {ProfileData} from "./ProfileData";

@Component({
    selector: 'profile',
    providers: [],
    styleUrls: ['./ProfileComponent.css'],
    templateUrl: './ProfileComponent.html'
})
export class ProfileComponent {

    public profileForm = this.formBuilder.group({
    });

    constructor(public formBuilder: FormBuilder,
                private http: Http) {
    }
}
