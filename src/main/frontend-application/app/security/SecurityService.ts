import {Injectable, OnInit} from "@angular/core";
import {SecurityStatus} from "./SecurityStatus";
import {Http} from "@angular/http";

@Injectable()
export class SecurityService {

    constructor(private securityStatus: SecurityStatus,
                private http: Http) {
    }

    initialize() {
        this.updateSecurityStatus();
    }

    updateSecurityStatus(): void {
        this.http.get("api/security/status")
            .subscribe(
                data => {
                    this.securityStatus.username = data.json().username;
                    this.securityStatus.loggedIn = this.securityStatus.username !== null;
                },
                error => {
                    this.securityStatus.username = undefined;
                    this.securityStatus.loggedIn = false;
                });
    }

    logout() {
        this.http.post("api/logout", null)
            .subscribe(
                data => {
                    this.updateSecurityStatus();
                },
                error => {
                    this.updateSecurityStatus();
                });
    }
}