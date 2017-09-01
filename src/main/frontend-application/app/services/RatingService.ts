import {Injectable} from "@angular/core";
import {AbstractApiService} from "./AbstractApiService";
import {Http} from "@angular/http";
import {Rating} from "../ratings/Rating";
import {RideJoin} from "../rides/model/RideJoin";

@Injectable()
export class RatingService extends AbstractApiService {

    private profileApiUrl = 'api/ratings';

    constructor(private http: Http) {
        super();
    }

    createRatingForRideJoin(rating: Rating, rideJoin: RideJoin): Promise<Rating> {
        return this.http
            .post(`${this.profileApiUrl}/createRatingForRideJoin`, {rating, rideJoin}, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as Rating)
            .catch(this.handleError);
    }
}