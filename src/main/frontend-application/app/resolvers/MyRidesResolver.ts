import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {Ride} from "../rides/create/Ride";
import {RideService} from "../services/RideService";

@Injectable()
export class MyRidesResolver implements Resolve<Ride[]> {
    constructor(private rideService: RideService, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<Ride[]> {
        return this.rideService.getMyRides().then(rides => {
            if (rides) {
                return rides;
            } else {
                // TODO show message that rides don't exist
                return null;
            }
        });
    }
}