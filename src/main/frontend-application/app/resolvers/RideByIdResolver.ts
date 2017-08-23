import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {RideService} from "../services/RideService";
import {Ride} from "../rides/model/Ride";

@Injectable()
export class RideByIdResolver implements Resolve<Ride> {
    constructor(private rideService: RideService, private router: Router) {
    }

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