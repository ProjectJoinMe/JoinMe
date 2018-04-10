import {Injectable} from "@angular/core";
import {Message} from "./Message";
import {UserNotifications} from "./UserNotifications";
import {Observable} from "rxjs/Observable";
import {NotificationService} from "../services/NotificationService";
import {Subscription} from "rxjs/Subscription";

/**
 * Created by Nicole August 2017.
 */

@Injectable()
export class NotificationsService {

    public notifications: UserNotifications;
    private enabled: boolean = false;
    private activeSchedule: Subscription;

    constructor(private notificationService: NotificationService) {
        this.clear();
    }

    public setEnabled(enabled: boolean) {
        if (enabled && !this.enabled) {
            this.updateNow();
            this.activeSchedule = this.retrieveNotificationsOn(Observable.interval(5000));
        } else if (this.enabled) {
            this.activeSchedule.unsubscribe();
        }
        this.enabled = enabled;
    }

    public clear() {
        this.notifications = new UserNotifications();
    }

    markNotificationsAsRead() {
        this.notifications.list.forEach(value => value.read = true);
        this.notifications.unreadNotificationCount = 0;
        this.notificationService.markNotificationsAsRead();
    }

    private updateNow() {
        this.retrieveNotificationsOn(Observable.of(0));
    }

    private retrieveNotificationsOn(observable: Observable<number>) {
        return observable
            .switchMap(() => this.notificationService.getNotifications())
            .subscribe((notifications: UserNotifications) => {
                this.notifications = notifications;
            });
    }
}