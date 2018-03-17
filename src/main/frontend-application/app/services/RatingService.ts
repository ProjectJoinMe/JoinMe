import {Injectable} from "@angular/core";
import {AbstractApiService} from "./AbstractApiService";
import {Http} from "@angular/http";
import {Rating} from "../ratings/Rating";
import {RideJoin} from "../rides/model/RideJoin";
import {RideJoinRating} from "../rides/model/RideJoinRating";
import {Ride} from "../rides/model/Ride";

@Injectable()
export class RatingService extends AbstractApiService {

    private ratingApiUrl = 'api/ratings';

    constructor(private http: Http) {
        super();
    }

    createRatingForRideJoin(rating: Rating, rideJoin: RideJoin): Promise<Rating> {
        return this.http
            .post(`${this.ratingApiUrl}/create/${rideJoin.id}`, rating, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as Rating)
            .catch(this.handleError);
    }

    getRatingsForRide(ride: Ride): Promise<Rating []> {
        return this.http
            .get(`${this.ratingApiUrl}/ride/${ride.id}`, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as Rating [])
            .catch(this.handleError);
    }
}