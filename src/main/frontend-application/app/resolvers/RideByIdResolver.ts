/**
 * Created by Nicole August 2017.
 */
import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {RideService} from "../services/RideService";
import {Ride} from "../rides/model/Ride";

/**
 * Created by Nicole August 2017.
 */
@Injectable()
export class RideByIdResolver implements Resolve<Ride> {
    constructor(private rideService: RideService, private router: Router) {
    }

    /**
     * Resolves a ride by the provided id.
     * @param {ActivatedRouteSnapshot} route the current route used
     * @param {RouterStateSnapshot} state its state
     * @returns {Promise<Ride>} A Promise for the rides in question.
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<Ride> {
        let id = parseInt(route.params['id']);

        return this.rideService.getRide(id).then(ride => {
            if (ride) {
                return ride;
            } else { // id not found
                // TODO show message that ride does not exist
                return null;
            }
        });
    }
}