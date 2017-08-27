import {Injectable} from "@angular/core";
import {Http, URLSearchParams} from "@angular/http";
import {AbstractApiService} from "./AbstractApiService";
import {Ride} from "../rides/model/Ride";
import {RideJoin} from "../rides/model/RideJoin";
import {LatLng} from "../rides/model/LatLng";

@Injectable()
export class RideService extends AbstractApiService {

    private ridesApiUrl = 'api/rides';

    constructor(private http: Http) {
        super();
    }

    getRidesOf(username: string): Promise<Ride[]> {
        return this.http.get(`/api/profile/${username}/rides`)
            .toPromise()
            .then(response => response.json() as Ride[])
            .catch(this.handleError);
    }

    searchRides(start: LatLng | null, destination: LatLng | null, date: Date | null): Promise<Ride[]> {
        return this.http.post(`${this.ridesApiUrl}/search`, {
            startLocation: start,
            destinationLocation: destination,
            date: date
        })
            .toPromise()
            .then(response => response.json() as Ride[])
            .catch(this.handleError);
    }

    getRide(id: number): Promise<Ride> {
        return this.http.get(`${this.ridesApiUrl}/${id}`)
            .toPromise()
            .then(response => response.json() as Ride)
            .catch(this.handleError);
    }

    createRide(ride: Ride): Promise<Ride> {
        return this.http
            .post(`${this.ridesApiUrl}/createRide`, ride, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as Ride)
            .catch(this.handleError);
    }

    updateRide(ride: Ride): Promise<Ride> {
        return this.http
            .put(`${this.ridesApiUrl}/${ride.id}/updateRide`, ride, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as Ride)
            .catch(this.handleError);
    }

    deleteRide(ride: Ride): Promise<void> {
        return this.http
            .delete(`${this.ridesApiUrl}/${ride.id}/delete`)
            .toPromise()
            .then(res => undefined)
            .catch(this.handleError);
    }

    getRideJoins(rideId: number): Promise<RideJoin> {
        const url = `${this.ridesApiUrl}/${rideId}/joins`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as RideJoin[])
            .catch(this.handleError);
    }

    getJoinedRidesOf(username: string): Promise<Ride[]> {
        const url = `api/profile/${username}/joins`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as Ride[])
            .catch(this.handleError);
    }

    joinRide(ride: Ride): Promise<RideJoin> {
        return this.http
            .post(`${this.ridesApiUrl}/${ride.id}/join`, null, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as RideJoin)
            .catch(this.handleError);
    }

    unjoinRide(ride: Ride): Promise<void> {
        return this.http
            .post(`${this.ridesApiUrl}/${ride.id}/unjoin`, null, {headers: this.headers})
            .toPromise()
            .then(res => undefined)
            .catch(this.handleError);
    }
}