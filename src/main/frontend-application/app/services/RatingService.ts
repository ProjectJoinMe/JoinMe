import {Injectable} from "@angular/core";
import {AbstractApiService} from "./AbstractApiService";
import {Http} from "@angular/http";
import {Rating} from "../ratings/Rating";
import {RideJoin} from "../rides/model/RideJoin";
import {RideJoinRating} from "../rides/model/RideJoinRating";
import {Ride} from "../rides/model/Ride";

/**
 * Created by Alexander August 2017.
 */

@Injectable()
export class RatingService extends AbstractApiService {

    private ratingApiUrl = 'api/ratings';

    constructor(private http: Http) {
        super();
    }

    /**
     * Creates a new Rating by sending an http-request to the api
     * @param {Rating} rating the rating the user has given
     * @param {RideJoin} rideJoin the joined ride
     * @returns {Promise<Rating>} A Promise with the Rating in question
     */
    createRatingForRideJoin(rating: Rating, rideJoin: RideJoin): Promise<Rating> {
        return this.http
            .post(`${this.ratingApiUrl}/create/${rideJoin.id}`, rating, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as Rating)
            .catch(this.handleError);
    }

    /**
     * Getting a Rating for a specific ride
     * @param {Ride} ride the ride for which the rating is sought
     * @returns {Promise<Rating[]>} Promise for the Rating[] containing the ratings
     */
    getRatingsForRide(ride: Ride): Promise<Rating []> {
        return this.http
            .get(`${this.ratingApiUrl}/ride/${ride.id}`, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as Rating [])
            .catch(this.handleError);
    }
}