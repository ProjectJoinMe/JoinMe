import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component} from "@angular/core";
import {UserProfile} from "../UserProfile";
import {SecurityService} from ".../security/SecurityService";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MailValidator} from "../../../validators/MailValidator";

/**
 * Created by Alexander on 18.08.2017.
 */

@Component({
    selector: 'edit',
    providers: [],
    styleUrls: ['./ProfileEditComponent.css'],
    templateUrl: './ProfileEditComponent.html'
})
export class ProfileEditComponent {

    public userProfile: UserProfile;
    public editForm: FormGroup;
    public submitted: boolean = false;
    public submitDisabled: boolean = false;

    constructor(private http: Http,
                private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private router: Router) {
        this.route.data
            .subscribe((data: { userProfile: UserProfile }) => {
                this.userProfile = data.userProfile;
            });
    }

    ngOnInit() {
        this.editForm = this.formBuilder.group({
            firstName: [this.userProfile.firstName, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
            lastName: [this.userProfile.lastName, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
            email: [this.userProfile.email, [Validators.required, MailValidator.mailValidator()]],
            // email: ["", [Validators.required, MailValidator.mailValidator()], this.validateUniqueEmailPromise],
            description: ["ToDO"],
        });
    }

    public edit() {
        this.submitted = true;
        if (this.editForm.valid) {
            this.submitDisabled = true;

        }
    }
}