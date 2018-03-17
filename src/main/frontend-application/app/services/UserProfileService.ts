import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {UserProfile} from "../profile/UserProfile";
import {AbstractApiService} from "./AbstractApiService";
import {UserPassword} from "../profile/change_password/UserPassword";
import {PointOfInterest} from "../profile/proactive_matching/PointOfInterest";

@Injectable()
export class UserProfileService extends AbstractApiService {

    private profileApiUrl = 'api/profile';

    constructor(private http: Http) {
        super();
    }

    getProfile(username: string): Promise<UserProfile> {
        return this.http.get(`${this.profileApiUrl}/${username}`)
            .toPromise()
            .then(response => response.json() as UserProfile)
            .catch(this.handleError);
    }

    updateUserProfile(userProfile: UserProfile): Promise<UserProfile> {
        return this.http
            .put(`${this.profileApiUrl}/${userProfile.username}/update`, userProfile, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as UserProfile)
            .catch(this.handleError);
    }

    updateUserPassword(userPassword: UserPassword): Promise<UserProfile> {
        return this.http
            .put(`${this.profileApiUrl}/${userPassword.username}/updatePassword`, userPassword, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as UserProfile)
            .catch(this.handleError);
    }

    setProfilePicture(formData: FormData, username: string): Promise<UserProfile> {
        return this.http
            .put(`${this.profileApiUrl}/${username}/uploadProfilePicture`, formData) //no headers to define here, boundary has to be generated automatically
            .toPromise()
            .then(res => res.json() as UserProfile)
            .catch(this.handleError);
    }

    setCarPicture(formData: FormData, username: string): Promise<UserProfile> {
        return this.http
            .put(`${this.profileApiUrl}/${username}/uploadCarPicture`, formData) //no headers to define here, boundary has to be generated automatically
            .toPromise()
            .then(res => res.json() as UserProfile)
            .catch(this.handleError);
    }

    updatePointsOfInterest(poi: PointOfInterest[]): Promise<PointOfInterest[]> {
        return this.http
            .put(`${this.profileApiUrl}/updatePointsOfInterest`, poi)
            .toPromise()
            .catch(this.handleError);
    }

    getPointsOfInterest(): Promise<PointOfInterest[]> {
        return this.http.get(`${this.profileApiUrl}/getPointsOfInterest`)
            .toPromise()
            .then(response => response.json() as PointOfInterest[])
            .catch(this.handleError);
    }

    getAvgRatingOfUser(userName : String): Promise<number>{
        return this.http.get(`api/ratings/avgRating/${userName}`)
            .toPromise()
            .then(response => response.json() as number)
            .catch(this.handleError);
    }
}