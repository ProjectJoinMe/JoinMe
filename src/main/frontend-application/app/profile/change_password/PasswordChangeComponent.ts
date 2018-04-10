import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component, OnInit} from "@angular/core";
import {SecurityService} from ".../security/SecurityService";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserPassword} from "./UserPassword";
import {UserProfile} from "../UserProfile";
import {UserProfileService} from "../../services/UserProfileService";
import {MessageService} from "../../message_service/MessageService";

/**
 * Created by Alexander August 2017.
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
    public userProfile: UserProfile;
    private userPassword: UserPassword;

    constructor(private http: Http,
                private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private router: Router,
                private userProfileService: UserProfileService,
                private messageService: MessageService) {

        this.route.data
            .subscribe((data: { userProfile: UserProfile }) => {
                this.userProfile = data.userProfile;
            });

    }

    ngOnInit() {
        this.passwordForm = this.formBuilder.group({
            newPassword: ["", [Validators.required, Validators.minLength(6)]], //first password field
            newPasswordCheck: ["", [Validators.required, this.passwordsMatchValidator]], //second password field
        });
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
                this.messageService.setMessage("Passwort wurde erfolgreich geändert.", "success");
                this.router.navigate(['/profile', this.userProfile.username]);
            }).catch(reason => {
                this.submitDisabled = false;
                this.messageService.setMessage("Passwort konnte nicht geändert werden.", "failure");
            });
        }
    }

    private passwordsMatchValidator(control: AbstractControl): any {
        var passwordControl = control.root.get("newPassword");
        return passwordControl && (passwordControl.value === control.value)
            ? null : {
                passwordsMatch: {
                    valid: false
                }
            };
    }
}