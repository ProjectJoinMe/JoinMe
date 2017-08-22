import {Injectable} from "@angular/core";
import {Http, URLSearchParams} from "@angular/http";
import {Ride} from "../rides/create/Ride";
import {RideJoin} from "../rides/details/RideJoin";
import {AbstractApiService} from "./AbstractApiService";

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

    searchRides(start: string | null, destination: string | null, date: Date | null): Promise<Ride[]> {
        let data = new URLSearchParams();
        data.append('start', start);
        data.append('destination', destination);
        data.append('date', date && date.toJSON());
        return this.http.get(`${this.ridesApiUrl}/search`, {
            search: data
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