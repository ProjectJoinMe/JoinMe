import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {AbstractApiService} from "./AbstractApiService";
import {UserNotifications} from "../notifications/UserNotifications";
import {UserNotification} from "../notifications/UserNotification";

/**
 * Created by Nicole August 2017.
 */
@Injectable()
export class NotificationService extends AbstractApiService {

    constructor(private http: Http) {
        super();
    }

    /**
     * Creates a new Notification by sending an http-request to the api
     * @returns {Promise<UserNotifications>} Promise for the UserNotifications containing the notification
     */
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

    /**
     * Marking a Notification as read
     */
    markNotificationsAsRead(): void {
        this.http.post('/api/notifications/markAsRead', null)
            .toPromise()
            .catch(this.handleError);
    }
}