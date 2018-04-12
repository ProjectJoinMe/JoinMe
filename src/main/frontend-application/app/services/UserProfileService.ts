import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {UserProfile} from "../profile/UserProfile";
import {AbstractApiService} from "./AbstractApiService";
import {UserPassword} from "../profile/change_password/UserPassword";
import {PointOfInterest} from "../profile/proactive_matching/PointOfInterest";

/**
 * Created by Alexander August 2017
 */

@Injectable()
export class UserProfileService extends AbstractApiService {

    private profileApiUrl = 'api/profile';

    constructor(private http: Http) {
        super();
    }

    /**
     * Getting the profile for displaying
     * @param {string} username name of the user
     * @returns {Promise<UserProfile>} A Promise with the UserProfile in question
     */
    getProfile(username: string): Promise<UserProfile> {
        return this.http.get(`${this.profileApiUrl}/${username}`)
            .toPromise()
            .then(response => response.json() as UserProfile)
            .catch(this.handleError);
    }

    /**
     * Updating a UserProfile
     * @param {UserProfile} userProfile the updated userprofile
     * @returns {Promise<UserProfile>} A Promise with the UserProfile in question
     */
    updateUserProfile(userProfile: UserProfile): Promise<UserProfile> {
        return this.http
            .put(`${this.profileApiUrl}/${userProfile.username}/update`, userProfile, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as UserProfile)
            .catch(this.handleError);
    }

    /**
     * Updating the userPassword
     * @param {UserPassword} userPassword the new password
     * @returns {Promise<UserProfile>} A Promise with the UserProfile in question
     */
    updateUserPassword(userPassword: UserPassword): Promise<UserProfile> {
        return this.http
            .put(`${this.profileApiUrl}/${userPassword.username}/updatePassword`, userPassword, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as UserProfile)
            .catch(this.handleError);
    }

    /**
     * Setting a profile-picture
     * @param {FormData} formData form of the data
     * @param {string} username name of the user
     * @returns {Promise<UserProfile>} A Promise with the UserProfile in question
     */
    setProfilePicture(formData: FormData, username: string): Promise<UserProfile> {
        return this.http
            .put(`${this.profileApiUrl}/${username}/uploadProfilePicture`, formData) //no headers to define here, boundary has to be generated automatically
            .toPromise()
            .then(res => res.json() as UserProfile)
            .catch(this.handleError);
    }

    /**
     * Setting a car-picture
     * @param {FormData} formData form of the incoming data
     * @param {string} username name of the user
     * @returns {Promise<UserProfile>} A Promise with the UserProfile in question
     */
    setCarPicture(formData: FormData, username: string): Promise<UserProfile> {
        return this.http
            .put(`${this.profileApiUrl}/${username}/uploadCarPicture`, formData) //no headers to define here, boundary has to be generated automatically
            .toPromise()
            .then(res => res.json() as UserProfile)
            .catch(this.handleError);
    }

    /**
     * Changing the Point of Interest
     * @param {PointOfInterest[]} poi array with the points of interest
     * @returns {Promise<PointOfInterest[]>} Promise for the PointOfInterest[] containing the PointsOfInterests
     */
    updatePointsOfInterest(poi: PointOfInterest[]): Promise<PointOfInterest[]> {
        return this.http
            .put(`${this.profileApiUrl}/updatePointsOfInterest`, poi)
            .toPromise()
            .catch(this.handleError);
    }

    /**
     * Getting the PointsOfInterests
     * @returns {Promise<PointOfInterest[]>} Promise for the PointOfInterest[] containing the PointsOfInterests
     */
    getPointsOfInterest(): Promise<PointOfInterest[]> {
        return this.http
            .get(`${this.profileApiUrl}/getPointsOfInterest`)
            .toPromise()
            .then(response => response.json() as PointOfInterest[])
            .catch(this.handleError);
    }

    /**
     * Getting the average rating of a user
     * @param {String} userName name of the user
     * @returns {Promise<number>} A Promise with the number in question
     */
    getAvgRatingOfUser(userName: String): Promise<number> {
        return this.http
            .get(`api/ratings/avgRating/${userName}`)
            .toPromise()
            .then(response => response.json() as number)
            .catch(this.handleError);
    }
}