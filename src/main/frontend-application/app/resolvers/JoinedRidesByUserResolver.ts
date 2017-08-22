import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {Ride} from "../rides/create/Ride";
import {RideService} from "../services/RideService";

@Injectable()
export class JoinedRidesByUserResolver implements Resolve<Ride> {
    constructor(private rideService: RideService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<Ride[]> {
        let username = route.params['username'];
        return this.rideService.getJoinedRidesOf(username).then(rides => {
            return rides;
        });
    }
}