/**
 * Created by Alexander January 2018.
 *
 * daten vorher laden -> vorladen
 * resolve wird aufgeruften und bekommt über den parameter
 * holt sich den chatservice der zu dem user gehören
 */
import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {ChatMessages} from "../chat/ChatMessages";
import {ChatService} from "../services/ChatService";
import {ChatMessage} from "../chat/ChatMessage";

@Injectable()
export class ChatResolver implements Resolve<ChatMessage> {
    constructor(private chatService: ChatService, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<ChatMessage []> {
        let fromUserName: string = route.queryParams["fromUserName"];
        let toUserName: string = route.queryParams["toUserName"];

        return this.chatService.getChatMessages(fromUserName, toUserName).then(chatMessage => {
            return chatMessage;
        }).catch(reason => {
            return null;
        })
    }
}