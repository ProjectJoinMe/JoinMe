import {Injectable} from "@angular/core";
import {AbstractApiService} from "./AbstractApiService";
import {Http} from "@angular/http";
import {ChatMessage} from "../chat/ChatMessage";
import {ChatMessages} from "../chat/ChatMessages";

@Injectable()
export class ChatService extends AbstractApiService {

    private ratingApiUrl = 'api/chat';

    constructor(private http: Http) {
        super();
    }

    createChatMessage(chatMessage: ChatMessage): Promise<ChatMessage> {
        return this.http
            .post(`${this.ratingApiUrl}/create`, chatMessage, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as ChatMessage)
            .catch(this.handleError);
    }


    getChatMessages(fromUserName: string, toUserName: string): Promise<ChatMessage []> {
        return this.http
            .post(`${this.ratingApiUrl}/getMessages`, {fromUserName, toUserName}, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as ChatMessage [])
            .catch(this.handleError);
    }
}