/**
 * Created by Nicole August 2017.
 */
import {Component} from "@angular/core";
import {UserProfile} from "./UserProfile";
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityStatus} from "../security/SecurityStatus";
import {UserProfileService} from "../services/UserProfileService";

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
    public avgRatingForUser: number;

    constructor(private route: ActivatedRoute,
                public securityStatus: SecurityStatus,
                private router: Router,
                private userProfileService: UserProfileService) {
    }

    ngOnInit(): void {
        this.route.data
            .subscribe((data: { userProfile: UserProfile }) => {
                this.userProfile = data.userProfile;
            });
        this.userProfilePicturePath = "/api/profile/" + this.userProfile.username + "/profilePicture";
        this.userCarPicturePath = "/api/profile/" + this.userProfile.username + "/carPicture";
        this.userProfileService.getAvgRatingOfUser(this.userProfile.username).then(avgRatingForUser => {
            this.avgRatingForUser = avgRatingForUser;
        });
    }

    toUserChat() {
        this.router.navigate(['/chat', this.userProfile.username], {
            queryParams: {
                fromUserName: this.securityStatus.username,
                toUserName: this.userProfile.username
            }
        });
    }
}
