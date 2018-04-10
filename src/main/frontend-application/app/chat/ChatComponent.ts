import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {MessageService} from "../message_service/MessageService";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SecurityService} from "../security/SecurityService";
import {Http} from "@angular/http";
import {SecurityStatus} from "../security/SecurityStatus";
import {UserProfile} from "../profile/UserProfile";
import {UserProfileService} from "../services/UserProfileService";
import {ChatService} from "../services/ChatService";
import {ChatMessage} from "./ChatMessage";

/**
 * Created by Alexander January 2018.
 */

@Component({
    selector: 'chat',
    providers: [],
    styleUrls: ['./ChatComponent.css'],
    templateUrl: './ChatComponent.html'
})
export class ChatComponent implements OnInit {

    public userProfileChatTo: UserProfile;
    public userProfileChatFrom: UserProfile;
    public chatForm: FormGroup;
    public submitDisabled: boolean = false;
    public chatMessages: ChatMessage [];


    constructor(private http: Http,
                private securityService: SecurityService,
                private route: ActivatedRoute,
                private router: Router,
                private formBuilder: FormBuilder,
                private securityStatus: SecurityStatus,
                private messageService: MessageService,
                private userProfileService: UserProfileService,
                private chatService: ChatService) {
    }

    ngOnInit(): void {
        this.route.data
            .subscribe((data: { chatMessages: ChatMessage [] }) => {
                this.chatMessages = data.chatMessages
            });

        let fromUserName;
        let toUserName;

        this.route.queryParams.subscribe((params: Params) => {
            fromUserName = params['fromUserName'];
            toUserName = params['toUserName'];
        });

        this.userProfileService.getProfile(fromUserName).then(userProfile => {
            this.userProfileChatFrom = userProfile;
        }).catch(reason => {
            console.log(reason);
        });

        this.userProfileService.getProfile(toUserName).then(userProfile => {
            this.userProfileChatTo = userProfile;
        }).catch(reason => {
            console.log(reason);
        });

        this.chatForm = this.formBuilder.group({
            chatMessage: ["", [Validators.required]]
        });
    }


    sendChatMessage() {
        this.submitDisabled = true;
        if (this.chatForm.valid) {

            console.info("chatting...");
            let chatMessageString = <string> this.chatForm.get("chatMessage").value;

            let chatMessage: ChatMessage = <ChatMessage> {
                message: chatMessageString,
                toUser: this.userProfileChatTo,
                fromUser: this.userProfileChatFrom,
                creationDateTime: new Date()
            };

            this.chatService.createChatMessage(chatMessage).then(chatMessage => {
                this.submitDisabled = false;
                this.chatForm.get("chatMessage").setValue("");
                location.reload(); //reloads page
            }).catch(reason => {
                console.info(reason.toString());
                this.submitDisabled = false;
                this.messageService.setMessage("Senden fehlgeschlagen!", "failure")
            });
        }
        else {
            this.messageService.setMessage("Bitte Nachricht eingeben!", "failure")
        }
    }
}