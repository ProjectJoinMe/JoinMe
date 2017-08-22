import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component, OnInit} from "@angular/core";
import {SecurityService} from ".../security/SecurityService";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MailValidator} from "../../../validators/MailValidator";
import {UserProfileService} from "../../services/UserProfileService";
import {UserProfile} from "../UserProfile";

/**
 * Created by Alexander on 22.08.2017.
 */

@Component({
    selector: 'changeEmail',
    providers: [],
    styleUrls: ['./EmailChangeComponent.css'],
    templateUrl: './EmailChangeComponent.html'
})
export class EmailChangeComponent implements OnInit {

    public userProfile: UserProfile;
    public emailForm: FormGroup;
    public submitted: boolean = false;
    public submitDisabled: boolean = false;

    constructor(private http: Http,
                private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private router: Router,
                private userProfileService: UserProfileService) {
    }

    ngOnInit(): void {
        this.emailForm = this.formBuilder.group({
            email: ["", [Validators.required, MailValidator.mailValidator()]],
            // email: ["", [Validators.required, MailValidator.mailValidator()], this.validateUniqueEmailPromise],
        });

        this.route.data
            .subscribe((data: { userProfile: UserProfile }) => {
                this.userProfile = data.userProfile;
                this.emailForm.setValue({
                    email: this.userProfile.email
                });
            });
    }

    public change() {
        this.submitted = true;
        if (this.emailForm.valid) {
            console.info("updating email");
            this.submitDisabled = true;
            let email = <string> this.emailForm.get("email").value;
            this.userProfile.email = email;
            this.userProfileService.updateUserProfile(this.userProfile).then(updatedUserProfile => {
                this.router.navigate(['/profile', this.userProfile.username]);
            }).catch(reason => {
                this.submitDisabled = false;
                console.error("failed to update email, TODO message");
            });
        }
    }

}