export interface Ride {
    id?: number;
    start: string;
    startPlaceId: string;
    destination: string;
    destinationPlaceId: string;
    departureDateTime: Date;
    creationDate?: Date;
    returnDepartureDateTime: Date;
    maxPassengers: number;
    notes: string;
    providerUsername?: string;
}