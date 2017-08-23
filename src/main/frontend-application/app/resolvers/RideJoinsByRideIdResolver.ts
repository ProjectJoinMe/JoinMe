import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {RideService} from "../services/RideService";
import {Ride} from "../rides/model/Ride";

@Injectable()
export class RideJoinsByRideIdResolver implements Resolve<Ride> {
    constructor(private rideService: RideService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<Ride> {
        let id = parseInt(route.params['id']);

        return this.rideService.getRideJoins(id).then(rideJoins => {
            return rideJoins;
        });
    }
}