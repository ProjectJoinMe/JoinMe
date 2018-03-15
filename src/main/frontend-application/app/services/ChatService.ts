import {Injectable} from "@angular/core";
import {AbstractApiService} from "./AbstractApiService";
import {Http} from "@angular/http";
import {ChatMessage} from "../chat/ChatMessage";
import {UserProfile} from "../profile/UserProfile";
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

    getChatMessages(fromUser: UserProfile, toUser: UserProfile): Promise<ChatMessages> {
        return this.http
            .post(`${this.ratingApiUrl}/getMessages`, {fromUser, toUser}, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as ChatMessages)
            .catch(this.handleError);
    }
}