import {Component, OnInit} from "@angular/core";
import {RegistrationData} from "./RegistrationData";
import {Validators, FormGroup, FormBuilder, FormControl, AbstractControl} from "@angular/forms";
import {Http, URLSearchParams} from "@angular/http";
import {MailValidator} from "../../validators/MailValidator";
import cloneWith = require("lodash/cloneWith");
import {Router} from "@angular/router";
import {Observable} from "rxjs";

@Component({
    selector: 'registration',
    providers: [],
    styleUrls: ['./RegistrationComponent.css'],
    templateUrl: './RegistrationComponent.html'
})
export class RegistrationComponent implements OnInit {

    public registrationForm: FormGroup;
    public submitted: boolean = false;
    public maxDateOfBirth: Date = new Date();
    public submitDisabled: boolean = false;

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private http: Http) {
    }

    public register() {
        this.submitted = true;
        if (this.registrationForm.valid) {
            this.submitDisabled = true;
            let registrationData: RegistrationData = {
                username: <string> this.registrationForm.get("username").value,
                email: <string> this.registrationForm.get("email").value,
                dateOfBirth: new Date(this.registrationForm.get("dateOfBirth").value),
                gender: <string> this.registrationForm.get("gender").value,
                password: <string> this.registrationForm.get("password").value
            };
            this.http.post("api/accounts/register", registrationData)
                .subscribe(
                    data => {
                        this.submitDisabled = false;
                        this.router.navigate(['/login']);
                        // TODO success -> show confirmation message and move to login screen
                    },
                    error => {
                        this.submitDisabled = false;
                        console.error("failed to register account, TODO");
                    });
            // .catch(this.handleError) // TODO handle all unhandled errors generally by showing some message bar
        }
    }

    ngOnInit() {
        this.registrationForm = this.formBuilder.group({
            username: ["", [Validators.required, Validators.minLength(5), Validators.maxLength(20)]],
            email: ["", [Validators.required, MailValidator.mailValidator()]],
            // email: ["", [Validators.required, MailValidator.mailValidator()], this.validateUniqueEmailPromise],
            dateOfBirth: ["", [Validators.required]],
            gender: ["", Validators.required],
            password: ["", [Validators.required, Validators.minLength(6)]],
            passwordCheck: ["", [Validators.required, this.passwordsMatchValidator]],
        });
    }

    private passwordsMatchValidator(control: AbstractControl): any {
        // partially from https://scotch.io/tutorials/how-to-implement-a-custom-validator-directive-confirm-password-in-angular-2
        var passwordControl = control.root.get("password");
        return passwordControl && (passwordControl.value === control.value)
            ? null : {
            passwordsMatch: {
                valid: false
            }
        };
    }

    private validateUniqueEmailPromise(email: string) { // TODO make work
        return new Observable(observer => {
            var params = new URLSearchParams();
            // from https://angular.io/docs/ts/latest/guide/server-communication.html
            this.http.get("api/accounts/exists/byUsername/")
                .subscribe(
                    data => {
                        this.submitDisabled = false;
                        this.router.navigate(['/login']);
                        // TODO success -> show confirmation message and move to login screen
                    },
                    error => {
                        this.submitDisabled = false;
                        console.error("failed to register account, TODO");
                    });
            setTimeout(() => {
                if (email === "alreadyExistsEmail@gmail.com") {
                    observer.next({
                        mailUnique: {
                            valid: false
                        }
                    })
                } else {
                    observer.next(null);
                }
            }, 2000);
        })
    }

}
