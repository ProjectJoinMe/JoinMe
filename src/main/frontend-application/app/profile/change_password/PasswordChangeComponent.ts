import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component, OnInit} from "@angular/core";
import {SecurityService} from ".../security/SecurityService";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserPassword} from "./UserPassword";
import {UserProfile} from "../UserProfile";
import {UserProfileService} from "../../services/UserProfileService";

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
    private userPassword: UserPassword;
    public userProfile: UserProfile;

    constructor(private http: Http,
                private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private router: Router,
                private userProfileService: UserProfileService) {

        this.route.data
            .subscribe((data: { userProfile: UserProfile }) => {
                this.userProfile = data.userProfile;
            });

    }

    ngOnInit() {
        this.passwordForm = this.formBuilder.group({
            newPassword: ["", [Validators.required, Validators.minLength(6)]],
            newPasswordCheck: ["", [Validators.required, this.passwordsMatchValidator]],
        });
    }

    private passwordsMatchValidator(control: AbstractControl): any {
        // partially from https://scotch.io/tutorials/how-to-implement-a-custom-validator-directive-confirm-password-in-angular-2
        var passwordControl = control.root.get("newPassword");
        return passwordControl && (passwordControl.value === control.value)
            ? null : {
                passwordsMatch: {
                    valid: false
                }
            };
    }

    public changePassword() {
        this.submitted = true;
        if (this.passwordForm.valid) {
            console.info("updating password");
            this.submitDisabled = true;

            let password = <string> this.passwordForm.get("newPassword").value;

            this.userPassword = {
                username: this.userProfile.username,
                password: password
            };

            this.userProfileService.updateUserPassword(this.userPassword).then(updatedUserProfile => {
                this.router.navigate(['/profile', this.userProfile.username]);
            }).catch(reason => {
                this.submitDisabled = false;
                console.error("failed to update password, TODO message");
            });
        }
    }
}