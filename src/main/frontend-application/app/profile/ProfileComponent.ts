import {Component} from "@angular/core";
import {UserProfile} from "./UserProfile";
import {ActivatedRoute} from "@angular/router";
import {SecurityStatus} from "../security/SecurityStatus";
import cloneWith = require("lodash/cloneWith");

@Component({
    selector: 'profile',
    providers: [],
    styleUrls: ['./ProfileComponent.css'],
    templateUrl: './ProfileComponent.html'
})
export class ProfileComponent {

    userProfile: UserProfile;

    constructor(private route: ActivatedRoute,
                public securityStatus: SecurityStatus) {
    }

    ngOnInit(): void {
        this.route.data
            .subscribe((data: { userProfile: UserProfile }) => {
                this.userProfile = data.userProfile;
            });
    }
}
