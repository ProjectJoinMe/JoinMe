import {UserProfile} from "../profile/UserProfile";

export interface ChatMessage {
    message: string;
    toUser: UserProfile;
    fromUser: UserProfile;
    creationDateTime: Date;
}