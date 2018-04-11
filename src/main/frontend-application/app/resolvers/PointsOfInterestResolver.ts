/**
 * Created by Nicole August 2017.
 */
import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {PointOfInterest} from "../profile/proactive_matching/PointOfInterest";
import {UserProfileService} from "../services/UserProfileService";

/**
 * Created by Nicole January 2018.
 */
@Injectable()
export class PointsOfInterestResolver implements Resolve<PointOfInterest[]> {
    constructor(private userProfileService: UserProfileService, private router: Router) {
    }

    /**
     * Resolves the PointsOfInterest
     * @param {ActivatedRouteSnapshot} route the current route
     * @param {RouterStateSnapshot} state the state of it
     * @returns {Promise<PointOfInterest[]>} Promise for the array containing the PointsOfInterest
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<PointOfInterest[]> {
        return this.userProfileService.getPointsOfInterest().then(pois => {
            if (pois) {
                return pois;
            } else {
                // pois dont exist
                return null;
            }
        });
    }
}