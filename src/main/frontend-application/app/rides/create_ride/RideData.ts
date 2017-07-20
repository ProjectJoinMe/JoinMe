export interface RideData {
    start: string;
    destination: string;
    departureDateTime: Date;
    returnRide: boolean;
    returnDepartureDateTime: Date;
    maxPassengers: number;
    notes: string;
}