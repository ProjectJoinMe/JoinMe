import {Injectable, OnInit} from "@angular/core";
import {Message} from "./Message";
import {Http} from "@angular/http";
import {UserNotifications} from "./UserNotifications";

@Injectable()
export class NotificationsService {

    public notifications: UserNotifications;

    constructor() {
        this.notifications = new UserNotifications();
    }

}