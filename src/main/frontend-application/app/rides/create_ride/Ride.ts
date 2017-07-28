export interface Ride {
    start: string;
    destination: string;
    departureDateTime: Date;
    creationDate?: Date;
    returnDepartureDateTime: Date;
    maxPassengers: number;
    notes: string;
}