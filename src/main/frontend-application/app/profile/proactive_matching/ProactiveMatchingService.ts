import {Injectable, OnInit} from "@angular/core";
import {Message} from "./Message";
import {Http} from "@angular/http";
import {UserNotifications} from "./UserNotifications";
import {Observable} from "rxjs/Observable";
import {NotificationService} from "../services/NotificationService";
import {Subscription} from "rxjs/Subscription";

@Injectable()
export class ProactiveMatchingService {

    constructor(private proactiveMatchingService: ProactiveMatchingService) {
    }


}