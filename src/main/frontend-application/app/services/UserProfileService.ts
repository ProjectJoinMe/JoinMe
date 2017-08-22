import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {UserProfile} from "../profile/UserProfile";
import {AbstractApiService} from "./AbstractApiService";

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
}