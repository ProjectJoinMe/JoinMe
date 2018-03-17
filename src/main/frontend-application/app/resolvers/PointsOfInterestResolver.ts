import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {PointOfInterest} from "../profile/proactive_matching/PointOfInterest";
import {UserProfileService} from "../services/UserProfileService";

@Injectable()
export class PointsOfInterestResolver implements Resolve<PointOfInterest[]> {
    constructor(private userProfileService: UserProfileService, private router: Router) {
    }

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