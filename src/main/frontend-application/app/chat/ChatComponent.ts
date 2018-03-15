import {Component, OnInit, Pipe} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "../message_service/MessageService";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SecurityService} from "../security/SecurityService";
import {Http} from "@angular/http";
import {SecurityStatus} from "../security/SecurityStatus";
import {UserProfile} from "../profile/UserProfile";
import {UserProfileService} from "../services/UserProfileService";
import {ChatService} from "../services/ChatService";
import {ChatMessages} from "./ChatMessages";
import {ChatMessage} from "./ChatMessage";

@Component({
    selector: 'chat',
    providers: [],
    styleUrls: ['./ChatComponent.css'],
    templateUrl: './ChatComponent.html'
})
export class ChatComponent implements OnInit {

    userProfileChatTo: UserProfile;
    userProfileChatFrom: UserProfile;
    chatMessages: ChatMessages;
    chatForm: FormGroup;
    submitDisabled: boolean = false;
    messagesArray : ChatMessage [] = [];

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
            .subscribe((data: { userProfile: UserProfile }) => {
                this.userProfileChatTo = data.userProfile;
            });
        this.userProfileService.getProfile(this.securityStatus.username).then(userProfile => {
                this.userProfileChatFrom = userProfile;
                this.chatService.getChatMessages(this.userProfileChatFrom, this.userProfileChatTo).then(chatMessages => {
                        this.chatMessages = chatMessages;
                        this.setChatMessages();
                    }
                ).catch(reason => {
                    this.messageService.setMessage("Fehler beim Laden des Chats.", "failure");
                });
            }
        ).catch(reason => {
            this.messageService.setMessage("Fehler beim Laden des Chats.", "failure");
        });


        this.chatForm = this.formBuilder.group({
            chatMessage: ["", [Validators.required]]
        });
    }

    setChatMessages()
    {
        this.messagesArray = this.chatMessages.list;
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
            }).catch(reason => {
                console.info(reason.toString())
                this.submitDisabled = false;
                this.messageService.setMessage("Senden fehlgeschlagen!", "failure")
            });
        }
        else {
            this.messageService.setMessage("Bitte Nachricht eingeben!", "failure")
        }
    }
}