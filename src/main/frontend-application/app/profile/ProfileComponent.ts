import {Component} from "@angular/core";
import {UserProfile} from "./UserProfile";
import {ActivatedRoute, Router} from "@angular/router";
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
    public userCarPicturePath: string = "";

    constructor(private route: ActivatedRoute,
                public securityStatus: SecurityStatus,
                private router: Router) {
    }

    ngOnInit(): void {
        this.route.data
            .subscribe((data: { userProfile: UserProfile }) => {
                this.userProfile = data.userProfile;
            });
        this.userProfilePicturePath = "/api/profile/" + this.userProfile.username + "/profilePicture";
        this.userCarPicturePath = "/api/profile/" + this.userProfile.username + "/carPicture";
    }

    toUserChat() {
        this.router.navigate(['/chat', this.userProfile.username], {queryParams: {fromUserName: this.securityStatus.username, toUserName: this.userProfile.username}});
    }
}
