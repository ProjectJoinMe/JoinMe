import {Injectable} from "@angular/core";
import {AbstractApiService} from "./AbstractApiService";
import {Http} from "@angular/http";
import {ChatMessage} from "../chat/ChatMessage";
import {ChatMessages} from "../chat/ChatMessages";

/**
 * Created by Alexander August 2017.
 */

@Injectable()
export class ChatService extends AbstractApiService {

    private ratingApiUrl = 'api/chat';

    constructor(private http: Http) {
        super();
    }

    /**
     * Creates a new ChatMessage by sending an http-request to the api
     * @param {ChatMessage} chatMessage contains the components of the message (Userfrom/to, text etc.)
     * @returns {Promise<ChatMessage>} A Promise with the ChatMessage in question
     */
    createChatMessage(chatMessage: ChatMessage): Promise<ChatMessage> {
        return this.http
            .post(`${this.ratingApiUrl}/create`, chatMessage, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as ChatMessage)
            .catch(this.handleError);
    }

    /**
     * Getting ChatMessages
     * @param {string} fromUserName Name of the sending User
     * @param {string} toUserName Name of the receiving User
     * @returns {Promise<ChatMessage[]>} Promise for the ChatMessage[] containing the messages.
     */
    getChatMessages(fromUserName: string, toUserName: string): Promise<ChatMessage []> {
        return this.http
            .post(`${this.ratingApiUrl}/getMessages`, {fromUserName, toUserName}, {headers: this.headers})
            .toPromise()
            .then(res => res.json() as ChatMessage [])
            .catch(this.handleError);
    }
}