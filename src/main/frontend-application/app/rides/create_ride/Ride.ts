export interface Ride {
    start: string;
    destination: string;
    departureDateTime: Date;
    creationDate?: Date;
    returnRide: boolean;
    returnDepartureDateTime: Date;
    maxPassengers: number;
    notes: string;
}