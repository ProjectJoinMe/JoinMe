import {Component} from "@angular/core";
import {FormBuilder} from "@angular/forms";
import {Http} from "@angular/http";
import {SecurityService} from "../security/SecurityService";
import {Router} from "@angular/router";
import {MessageService} from "../message_service/MessageService";
import {NotificationsService} from "./NotificationsService";

@Component({
    selector: 'notifications',
    providers: [],
    styleUrls: ['./NotificationsComponent.css'],
    templateUrl: './NotificationsComponent.html'
})
export class NotificationsComponent {

    constructor(public formBuilder: FormBuilder,
                private http: Http,
                private securityService: SecurityService,
                private router: Router,
                private notificationsService: NotificationsService) {
    }

}
