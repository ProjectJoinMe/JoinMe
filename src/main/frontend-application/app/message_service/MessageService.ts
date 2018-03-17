import {Injectable} from "@angular/core";
import {Message} from "./Message";
import {Http} from "@angular/http";

@Injectable()
export class MessageService {

    constructor(public currentMessage: Message,
                private http: Http) {
    }

    setMessage(text: string, type: string): void {
        this.currentMessage.text = text;
        this.currentMessage.type = type;
    }

}