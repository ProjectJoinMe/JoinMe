
import {Day} from "../DayEnum";
export class RideData {
    public start: string;
    public destination: string;
    public departureDate: Date;
    public departureHour: number;
    public departureMinute: number;
    public returnRide: boolean;
    public returnDepartureDate: Date;
    public returnDepartureHour: number;
    public returnDepartureMinute: number;
    public freeSeats: number;
    public periodic: boolean;
    public periodicDays: Day[];
    public remark: string;
}