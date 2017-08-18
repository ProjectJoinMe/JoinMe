import {ActivatedRoute, Params, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component} from "@angular/core";
import {UserProfile} from "../UserProfile";
import {SecurityService} from ".../security/SecurityService";
/**
 * Created by Alexander on 18.08.2017.
 */

@Component({
    selector: 'editProfile',
    providers: [],
    styleUrls: ['./ProfileEditComponent.css'],
    templateUrl: './ProfileEditComponent.html'
})
export class ProfileEditComponent {

    userProfile: UserProfile;

    constructor(private http: Http,
                private route: ActivatedRoute,
                private router: Router) {
    }



}