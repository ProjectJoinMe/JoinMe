
import {Day} from "../DayEnum";
export class RideData {
    public start: string;
    public destination: string;
    public departureDay: Date;
    public departureHour: number;
    public departureMinute: number;
    public returnRide: boolean;
    public returnDay: Date;
    public returnHour: number;
    public returnMinute: number;
    public freeSeats: number;
    public periodic: boolean;
    public periodicDays: Day[];
}