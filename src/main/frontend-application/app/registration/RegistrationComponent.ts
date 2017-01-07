import {Component} from '@angular/core';
import {RegistrationData} from "./RegistrationData";
import cloneWith = require("lodash/cloneWith");
import {Validators, FormControl, FormGroup, ReactiveFormsModule, FormBuilder} from "@angular/forms";
import {Http, Response} from "@angular/http";

@Component({
    selector: 'registration',
    providers: [],
    styleUrls: ['./RegistrationComponent.css'],
    templateUrl: './RegistrationComponent.html'
})
export class RegistrationComponent {

    public registrationForm = this.formBuilder.group({
        username: ["", Validators.required],
        email: ["", Validators.required],
        password: ["", Validators.required],
        passwordCheck: ["", Validators.required],
    });

    // TODO validate username does not exist by validating asynchronously in custom validator
    // TODO validate

    constructor(public formBuilder: FormBuilder,
                private http: Http) {
    }

    public doRegister() {
        console.log("hallo!");
        let registrationData: RegistrationData = {
            username: <string> this.registrationForm.get("username").value,
            email: <string> this.registrationForm.get("email").value,
            password: <string> this.registrationForm.get("password").value,
        };
        this.http.post("api/accounts/register", registrationData)
            .subscribe(
                data => {
                    // TODO success -> show confirmation message and move to login screen
                },
                error => {
                    console.error("failed to register account, TODO")
                });
        // .catch(this.handleError) // TODO handle all unhandled errors generally by showing some message bar
    }
}
