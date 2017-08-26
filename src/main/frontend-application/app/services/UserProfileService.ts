import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {UserProfile} from "../profile/UserProfile";
import {AbstractApiService} from "./AbstractApiService";
import {UserPassword} from "../profile/change_password/UserPassword";

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
}