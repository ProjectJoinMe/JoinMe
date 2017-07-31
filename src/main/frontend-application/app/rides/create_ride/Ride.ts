export interface Ride {
    id?: number;
    start: string;
    destination: string;
    departureDateTime: Date;
    creationDate?: Date;
    returnDepartureDateTime: Date;
    maxPassengers: number;
    notes: string;
}