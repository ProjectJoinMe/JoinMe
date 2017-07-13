
import {WeekDay} from "../WeekDay";

export interface RideData {
    start: string;
    destination: string;
    departureDate: Date;
    departureHour: number;
    departureMinute: number;
    returnRide: boolean;
    returnDepartureDate: Date;
    returnDepartureHour: number;
    returnDepartureMinute: number;
    freeSeats: number;
    periodicDays: WeekDay[];
    remark: string;
}