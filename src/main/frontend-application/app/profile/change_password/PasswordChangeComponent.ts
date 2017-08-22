import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component, OnInit} from "@angular/core";
import {SecurityService} from ".../security/SecurityService";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";

/**
 * Created by Alexander on 22.08.2017.
 */

@Component({
    selector: 'changePassword',
    providers: [],
    styleUrls: ['./PasswordChangeComponent.css'],
    templateUrl: './PasswordChangeComponent.html'
})
export class PasswordChangeComponent implements OnInit {

    public passwordForm: FormGroup;
    public submitted: boolean = false;
    public submitDisabled: boolean = false;

    constructor(private http: Http,
                private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private router: Router) {

    }

    ngOnInit() {
        this.passwordForm = this.formBuilder.group({
            oldPassword: ["", [Validators.required]],
            newPassword: ["", [Validators.required, Validators.minLength(6)]],
            newPasswordCheck: ["", [Validators.required]],
        });
    }

    /*private passwordsMatchValidator(control: AbstractControl): any {
        // partially from https://scotch.io/tutorials/how-to-implement-a-custom-validator-directive-confirm-password-in-angular-2
        var passwordControl = control.root.get("newPassword");
        return passwordControl && (passwordControl.value === control.value)
            ? null : {
                passwordsMatch: {
                    valid: false
                }
            };
    }*/

    public change() {

    }
}