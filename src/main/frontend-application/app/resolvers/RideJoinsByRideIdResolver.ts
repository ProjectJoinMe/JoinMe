/**
 * Created by Nicole August 2017.
 */
import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {RideService} from "../services/RideService";
import {Ride} from "../rides/model/Ride";

/**
 * Created by Alexander August 2017.
 */
@Injectable()
export class RideJoinsByRideIdResolver implements Resolve<Ride> {
    constructor(private rideService: RideService) {
    }

    /**
     * Resolves the RideJoins for a ride specified by the id in the request.
     * @param {ActivatedRouteSnapshot} route the current route
     * @param {RouterStateSnapshot} state the state of the current route
     * @returns {Promise<Ride>} A Promise with the RideJoins in question
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<Ride> {
        let id = parseInt(route.params['id']);

        return this.rideService.getRideJoins(id).then(rideJoins => {
            return rideJoins;
        });
    }
}