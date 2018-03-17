import {Injectable} from "@angular/core";
import {SecurityStatus} from "./SecurityStatus";
import {Http} from "@angular/http";
import {NotificationsService} from "../notifications/NotificationsService";

@Injectable()
export class SecurityService {

    constructor(private securityStatus: SecurityStatus,
                private notificationsService: NotificationsService,
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
                    this.updateNotificationService();
                },
                error => {
                    this.securityStatus.username = null;
                    this.securityStatus.loggedIn = false;
                    this.updateNotificationService();
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

    private updateNotificationService() {
        this.notificationsService.clear();
        this.notificationsService.setEnabled(this.securityStatus.loggedIn);
    }
}