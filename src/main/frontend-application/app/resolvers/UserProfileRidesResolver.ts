import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {RideService} from "../services/RideService";
import {Ride} from "../rides/model/Ride";

/**
 * Created by Nicole August 2017.
 */
@Injectable()
export class MyRidesResolver implements Resolve<Ride[]> {
    constructor(private rideService: RideService, private router: Router) {
    }

    /**
     *  Resolves the Rides for the user in question.
     * @param {ActivatedRouteSnapshot} route the current route
     * @param {RouterStateSnapshot} state its state
     * @returns {Promise<Ride[]>} Promise containing the Ride[]
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<Ride[]> {
        let username = route.params['username'];
        return this.rideService.getRidesOf(username).then(rides => {
            if (rides) {
                return rides;
            } else {
                // TODO show message that rides don't exist
                return null;
            }
        });
    }
}