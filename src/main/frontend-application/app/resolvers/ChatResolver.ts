import {Crisis, CrisisService} from "./crisis.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {ChatMessages} from "../chat/ChatMessages";
import {ChatService} from "../services/ChatService";

@Injectable()
export class ChatResolver implements Resolve<ChatMessages> {
    constructor(private chatService: ChatService, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<ChatMessages> {
        let fromUserName: string = route.queryParams["fromUserName"];
        let toUserName: string = route.queryParams["toUserName"];

        return this.chatService.getChatMessages(fromUserName, toUserName).then(chatMessages => {
            return chatMessages;
        }).catch(reason => {
            return null;
        })
    }
}