/**
 * Created by Nicole August 2017.
 */
import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {UserProfile} from "../profile/UserProfile";
import {UserProfileService} from "../services/UserProfileService";

/**
 * Created by Nicole January 2017.
 */
@Injectable()
export class UserProfileByUsernameResolver implements Resolve<UserProfile> {
    constructor(private userProfileService: UserProfileService, private router: Router) {
    }

    /**
     * Resolves the userProfile for the specified username.
     * @param {ActivatedRouteSnapshot} route the current route
     * @param {RouterStateSnapshot} state the state of it
     * @returns {Promise<UserProfile>} Promise containing the userProfile.
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<UserProfile> {
        let username = route.params['username'];

        if (username) {
            return this.userProfileService.getProfile(username).then(userProfile => {
                if (userProfile) {
                    return userProfile;
                } else { // user not found
                    return null;
                }
            });
        }
    }
}