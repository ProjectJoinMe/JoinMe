import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {AbstractApiService} from "./AbstractApiService";
import {Ride} from "../rides/model/Ride";
import {RideJoin} from "../rides/model/RideJoin";
import {LatLng} from "../rides/model/LatLng";

/**
 * Created by Nicole August 2017
 */

@Injectable()
export class RideService extends AbstractApiService {

    private ridesApiUrl = 'api/rides';

    constructor(private http: Http) {
        super();
    }

    /**
     * Getting the ride of a specific user
     * @param {string} username the name of the user
     * @returns {Promise<Ride[]>} Promise for the Ride[] containing the rides
     */
    getRidesOf(username: string): Promise<Ride[]> {
        return this.http.get(`/api/profile/${username}/rides`)
            .toPromise()
            .then(response => response.json() as Ride[])
            .catch(this.handleError);
    }

    /**
     * Searching for rides for this criteria
     * @param {LatLng | null} start place of departures
     * @param {LatLng | null} destination place of destination
     * @param {Date | null} date date of the departure
     * @returns {Promise<Ride[]>} Promise for the Ride[] containing the rides
     */
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

    /**
     * Getting a specific ride with the id
     * @param {number} id the ride id
     * @returns {Promise<Ride>} A Promise with the Ride in question
     */
    getRide(id: number): Promise<Ride> {
        return this.http.get(`${this.ridesApiUrl}/${id}`)
            .toPromise()
            .then(response => response.json() as Ride)
            .catch(this.handleError);
    }

    /**
     * Creating a new ride
     * @param {Ride} ride a ride containing all components
     * @returns {Promise<Ride>} A Promise with the Ride in question
     */
    createRide(ride: Ride): Promise<Ride> {
        return this.http
            .post(`${this.ridesApiUrl}/createRide`, ride, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as Ride)
            .catch(this.handleError);
    }

    /**
     * For editing a ride
     * @param {Ride} ride the ride that was edited
     * @returns {Promise<Ride>} A Promise with the Ride in question
     */
    updateRide(ride: Ride): Promise<Ride> {
        return this.http
            .put(`${this.ridesApiUrl}/${ride.id}/updateRide`, ride, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as Ride)
            .catch(this.handleError);
    }

    /**
     * Deletes an existing ride
     * @param {Ride} ride the ride that is deleted
     * @returns {Promise<void>} Promise that the Ride was deleted
     */
    deleteRide(ride: Ride): Promise<void> {
        return this.http
            .delete(`${this.ridesApiUrl}/${ride.id}/delete`)
            .toPromise()
            .then(res => undefined)
            .catch(this.handleError);
    }

    /**
     * For displaying the RideJoins
     * @param {number} rideId the id of the joined ride
     * @returns {Promise<RideJoin>} A Promise with the RideJoins in question
     */
    getRideJoins(rideId: number): Promise<RideJoin> {
        const url = `${this.ridesApiUrl}/${rideId}/joins`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as RideJoin[])
            .catch(this.handleError);
    }

    /**
     * For displaying the joined rides of a user
     * @param {string} username the name of the user
     * @returns {Promise<Ride[]>} Promise for the Ride[] containing the Rides
     */
    getJoinedRidesOf(username: string): Promise<Ride[]> {
        const url = `api/profile/${username}/joins`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as Ride[])
            .catch(this.handleError);
    }

    /**
     * For joining rides
     * @param {Ride} ride the joined ride
     * @returns {Promise<RideJoin>} A Promise with the RideJoins in question
     */
    joinRide(ride: Ride): Promise<RideJoin> {
        return this.http
            .post(`${this.ridesApiUrl}/${ride.id}/join`, null, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as RideJoin)
            .catch(this.handleError);
    }

    /**
     * For unjoining rides
     * @param {Ride} ride the unjoined ride
     * @returns {Promise<void>} Promise that the ride was unjoined
     */
    unjoinRide(ride: Ride): Promise<void> {
        return this.http
            .post(`${this.ridesApiUrl}/${ride.id}/unjoin`, null, {headers: this.headers})
            .toPromise()
            .then(res => undefined)
            .catch(this.handleError);
    }

    /**
     * For receiving routing information
     * @param {Ride} ride the ride for the routing information
     * @returns {Promise<Ride>} A Promise with the Ride in question
     */
    getRouteInfo(ride: Ride): Promise<Ride> {
        return this.http.post(`${this.ridesApiUrl}/routeInformation`, ride)
            .toPromise()
            .then(response => response.json() as Ride)
            .catch(this.handleError);
    }
}