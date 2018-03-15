import {UserProfile} from "../profile/UserProfile";

export class ChatMessage {
    public message: string;
    public toUser: UserProfile;
    public fromUser: UserProfile;
    public creationDateTime: Date;
}