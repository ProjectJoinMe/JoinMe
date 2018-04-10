/**
 * Created by Alexander January 2018.
 */
import {UserProfile} from "../profile/UserProfile";

export interface ChatMessage {
    message: string;
    toUser: UserProfile;
    fromUser: UserProfile;
    creationDateTime: Date;
}