import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {UserProfile} from "../UserProfile";
import {SecurityService} from ".../security/SecurityService";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserProfileService} from "../../services/UserProfileService";
import {MessageService} from "../../message_service/MessageService";

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
    @ViewChild('profilePictureFileInput') profilePictureEl: ElementRef;
    @ViewChild('carPictureFileInput') carPictureEl: ElementRef;

    public userProfile: UserProfile;
    public editForm: FormGroup;
    public profileSubmitted: boolean = false;
    public profileSubmitDisabled: boolean = false;
    public userProfilePicturePath: string = "";
    public userCarPicturePath: string = "";
    public date: Date = new Date();

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
        this.userProfilePicturePath = "/api/profile/" + this.userProfile.username + "/profilePicture";
        this.userCarPicturePath = "/api/profile/" + this.userProfile.username + "/carPicture";
    }

    ngOnInit() {
        this.editForm = this.formBuilder.group({
            firstName: [this.userProfile.firstName, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
            lastName: [this.userProfile.lastName, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
            description: [this.userProfile.description],
            carMake: [this.userProfile.carMake],
            carModel: [this.userProfile.carModel],
            carManufacturingYear: [this.userProfile.carManufacturingYear, [this.carManufacturingYearValidator]],
            carDescription: [this.userProfile.carDescription]
        });

    }

    private carManufacturingYearValidator(control: AbstractControl): any {
        return !control.value.isNaN() && control.value > 1900 && control.value < this.date.getFullYear();
    }

    public edit() {
        this.profileSubmitted = true;
        if (this.editForm.valid) {
            console.info("updating profile");
            this.profileSubmitDisabled = true;

            let firstName = <string> this.editForm.get("firstName").value;
            let lastName = <string> this.editForm.get("lastName").value;
            let description = <string> this.editForm.get("description").value;
            let carMake = <string> this.editForm.get("carMake").value;
            let carModel = <string> this.editForm.get("carModel").value;
            let carManufacturingYear = <number> this.editForm.get("carManufacturingYear").value;
            let carDescription = <string> this.editForm.get("carDescription").value;

            this.userProfile.firstName = firstName;
            this.userProfile.lastName = lastName;
            this.userProfile.description = description;
            this.userProfile.carMake = carMake;
            this.userProfile.carModel = carModel;
            this.userProfile.carManufacturingYear = carManufacturingYear;
            this.userProfile.carDescription = carDescription;

            // this.uploadProfilePicture();
            // this.uploadCarPicture();

            this.userProfileService.updateUserProfile(this.userProfile).then(updatedUserProfile => {
                this.messageService.setMessage("Profil wurde erfolgreich geändert.", "success");
                this.router.navigate(['/profile', this.userProfile.username]);
            }).catch(reason => {
                this.profileSubmitDisabled = false;
                this.messageService.setMessage("Profil konnte nicht geändert werden.", "failure");
            });
        }
    }

    public uploadProfilePicture() {
        //partially from https://stackoverflow.com/questions/36352405/file-upload-with-angular2-to-rest-api

        let inputEl: HTMLInputElement = this.profilePictureEl.nativeElement;
        let fileCount: number = inputEl.files.length;
        let formData = new FormData();
        if (fileCount === 1) { // a file was selected
            formData.append('profilePicture', inputEl.files.item(0));
            this.userProfileService.setProfilePicture(formData, this.userProfile.username).then(updatedUserProfile => {
                // this.router.navigate(['/profile', this.userProfile.username]);
            }).catch(reason => {
                console.error("failed to update profile picture, TODO message");
            });
        }
    }

    public uploadCarPicture() {
        //partially from https://stackoverflow.com/questions/36352405/file-upload-with-angular2-to-rest-api

        let inputEl: HTMLInputElement = this.carPictureEl.nativeElement;
        let fileCount: number = inputEl.files.length;
        let formData = new FormData();
        if (fileCount === 1) { // a file was selected
            formData.append('carPicture', inputEl.files.item(0));
            this.userProfileService.setCarPicture(formData, this.userProfile.username).then(updatedUserProfile => {
                // this.router.navigate(['/profile', this.userProfile.username]);
            }).catch(reason => {
                console.error("failed to update car picture, TODO message");
            });
        }
    }
}