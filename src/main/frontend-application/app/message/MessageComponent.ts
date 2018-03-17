import {Component} from "@angular/core";
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {MessageService} from "../message_service/MessageService";

@Component({
    selector: 'message',
    providers: [],
    styleUrls: ['./MessageComponent.css'],
    templateUrl: './MessageComponent.html'
})
export class MessageComponent {

    constructor(private http: Http,
                private router: Router,
                public messageService: MessageService) {
    }

    public closeMessage() {
        this.messageService.setMessage(null, null);
    }

}