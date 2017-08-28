import {LatLng} from "./LatLng";

export interface RideRoute {
    pathLocations: LatLng[];
    suggestedPricePerPassenger: number;
}