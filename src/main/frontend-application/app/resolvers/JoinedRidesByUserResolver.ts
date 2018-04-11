import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {RideService} from "../services/RideService";
import {Ride} from "../rides/model/Ride";

/**
 * Created by Nicole August 2017.
 */
@Injectable()
export class JoinedRidesByUserResolver implements Resolve<Ride> {
    constructor(private rideService: RideService) {
    }

    /**
     * Resolves the rides joined by a user.
     * uses RideService
     * @param {ActivatedRouteSnapshot} route the current route
     * @param {RouterStateSnapshot} state the current state of the route
     * @returns {Promise<Ride[]>} Promise for an array containing the rides in question
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<Ride[]> {
        let username = route.params['username'];
        route.queryParams;
        return this.rideService.getJoinedRidesOf(username).then(rides => {
            return rides;
        });
    }
}