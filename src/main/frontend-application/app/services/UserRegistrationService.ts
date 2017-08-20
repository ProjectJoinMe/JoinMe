import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Ride} from "../rides/create/Ride";
import {RegistrationData} from "../registration/RegistrationData";
import {AbstractApiService} from "./AbstractApiService";

@Injectable()
export class UserRegistrationService extends AbstractApiService {

    private accountsApiUrl = 'api/accounts';

    constructor(private http: Http) {
        super();
    }

    register(registrationData: RegistrationData): Promise<Ride> {
        return this.http
            .post(`${this.accountsApiUrl}/register`, registrationData, {headers: this.headers})
            .toPromise()
            .then(res => null)
            .catch(this.handleError);
    }
}