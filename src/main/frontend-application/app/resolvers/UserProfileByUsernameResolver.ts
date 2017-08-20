import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {UserProfile} from "../profile/UserProfile";
import {UserProfileService} from "../services/UserProfileService";

@Injectable()
export class UserProfileByUsernameResolver implements Resolve<UserProfile> {
    constructor(private userProfileService: UserProfileService, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<UserProfile> {
        let username = route.params['username'];

        return this.userProfileService.getProfile(username).then(userProfile => {
            if (userProfile) {
                return userProfile;
            } else { // user not found
                // TODO show message that user does not exist
                return null;
            }
        });
    }
}