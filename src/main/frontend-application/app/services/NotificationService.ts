import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {AbstractApiService} from "./AbstractApiService";
import {UserNotifications} from "../notifications/UserNotifications";
import {UserNotification} from "../notifications/UserNotification";

@Injectable()
export class NotificationService extends AbstractApiService {

    constructor(private http: Http) {
        super();
    }

    getNotifications(): Promise<UserNotifications> {
        return this.http.get('/api/notifications')
            .toPromise()
            .then(response => {
                let notificationList = response.json() as UserNotification[];
                let userNotifications = new UserNotifications();
                userNotifications.list = notificationList;
                userNotifications.unreadNotificationCount = notificationList.filter(a => !a.read).length;
                return userNotifications;
            })
            .catch(this.handleError);
    }

    markNotificationsAsRead(): void {
        this.http.post('/api/notifications/markAsRead', null)
            .toPromise()
            .catch(this.handleError);
    }
}