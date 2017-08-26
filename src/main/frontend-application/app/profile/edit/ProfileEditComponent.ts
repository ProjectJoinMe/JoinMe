import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
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
    @ViewChild('fileInput') inputEl: ElementRef;

    public userProfile: UserProfile;
    public editForm: FormGroup;
    public profileSubmitted: boolean = false;
    public profileSubmitDisabled: boolean = false;
    public userProfilePicturePath: string = "";

    constructor(private http: Http,
                private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private router: Router,
                private userProfileService: UserProfileService) {
        this.route.data
            .subscribe((data: { userProfile: UserProfile }) => {
                this.userProfile = data.userProfile;
            });
        this.userProfilePicturePath = "/api/profile/" + this.userProfile.username + "/profilePicture"
    }

    ngOnInit() {
        this.editForm = this.formBuilder.group({
            firstName: [this.userProfile.firstName, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
            lastName: [this.userProfile.lastName, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
            description: [this.userProfile.description],
        });

    }

    public edit() {
        this.profileSubmitted = true;
        if (this.editForm.valid) {
            console.info("updating profile");
            this.profileSubmitDisabled = true;

            let firstName = <string> this.editForm.get("firstName").value;
            let lastName = <string> this.editForm.get("lastName").value;
            let description = <string> this.editForm.get("description").value;

            this.userProfile.firstName = firstName;
            this.userProfile.lastName = lastName;
            this.userProfile.description = description;

            this.userProfileService.updateUserProfile(this.userProfile).then(updatedUserProfile => {
                this.router.navigate(['/profile', this.userProfile.username]);
            }).catch(reason => {
                this.profileSubmitDisabled = false;
                console.error("failed to update profile, TODO message");
            });
        }
    }

    public upload() {
        //partially from https://stackoverflow.com/questions/36352405/file-upload-with-angular2-to-rest-api

        let inputEl: HTMLInputElement = this.inputEl.nativeElement;
        let fileCount: number = inputEl.files.length;
        let formData = new FormData();
        if (fileCount === 1) { // a file was selected
            formData.append('pictureFile', inputEl.files.item(0));
            this.userProfileService.setProfilePicture(formData, this.userProfile.username).then(updatedUserProfile => {
                this.router.navigate(['/profile', this.userProfile.username]);
            }).catch(reason => {
                console.error("failed to update profile picture, TODO message");
            });
        }
    }
}