
import {WeekDay} from "../WeekDay";

export interface RideData {
    start: string;
    destination: string;
    departureDateTime: Date;
    returnRide: boolean;
    returnDepartureDateTime: Date;
    freeSeats: number;
    notes: string;
}