import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component, OnInit} from "@angular/core";
import {UserProfile} from "../UserProfile";
import {SecurityService} from ".../security/SecurityService";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserProfileService} from "../../services/UserProfileService";

/**
 * Created by Alexander on 18.08.2017.
 */

@Component({
    selector: 'edit',
    providers: [],
    styleUrls: ['./ProfileEditComponent.css'],
    templateUrl: './ProfileEditComponent.html'
})
export class ProfileEditComponent implements OnInit {

    public userProfile: UserProfile;
    public editForm: FormGroup;
    public submitted: boolean = false;
    public submitDisabled: boolean = false;

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
        this.editForm = this.formBuilder.group({
            firstName: [this.userProfile.firstName, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
            lastName: [this.userProfile.lastName, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
            description: [this.userProfile.description],
        });
    }

    public edit() {
        this.submitted = true;
        if (this.editForm.valid) {
            console.info("updating profile");
            this.submitDisabled = true;

            let firstName = <string> this.editForm.get("firstName").value;
            let lastName = <string> this.editForm.get("lastName").value;
            let description = <string> this.editForm.get("description").value;

            this.userProfile.firstName = firstName;
            this.userProfile.lastName = lastName;
            this.userProfile.description = description;

            this.userProfileService.updateUserProfile(this.userProfile).then(updatedUserProfile => {
                this.router.navigate(['/profile', this.userProfile.username]);
            }).catch(reason => {
                this.submitDisabled = false;
                console.error("failed to update profile, TODO message");
            });
        }
    }
}