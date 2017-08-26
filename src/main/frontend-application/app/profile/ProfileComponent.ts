import {Component} from "@angular/core";
import {UserProfile} from "./UserProfile";
import {ActivatedRoute} from "@angular/router";
import {SecurityStatus} from "../security/SecurityStatus";

@Component({
    selector: 'profile',
    providers: [],
    styleUrls: ['./ProfileComponent.css'],
    templateUrl: './ProfileComponent.html'
})
export class ProfileComponent {

    userProfile: UserProfile;
    public userProfilePicturePath: string = "";

    constructor(private route: ActivatedRoute,
                public securityStatus: SecurityStatus) {
    }

    ngOnInit(): void {
        this.route.data
            .subscribe((data: { userProfile: UserProfile }) => {
                this.userProfile = data.userProfile;
            });
        this.userProfilePicturePath = "/api/profile/" + this.userProfile.username + "/profilePicture"
    }
}
