import {RideRoute} from "./RideRoute";

export interface Ride {
    id?: number;
    start?: string;
    startPlaceId?: string;
    destination?: string;
    destinationPlaceId?: string;
    departureDateTime?: Date;
    creationDate?: Date;
    returnDepartureDateTime?: Date;
    maxPassengers?: number;
    freeSeats?: number;
    notes?: string;
    providerUsername?: string;
    pricePerPassenger?: number;
    periodic?: boolean;
    periodicWeekDays?: number[];
    route?: RideRoute;
}