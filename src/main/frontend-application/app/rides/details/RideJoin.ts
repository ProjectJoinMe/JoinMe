import {UserProfile} from "../../profile/UserProfile";

export interface RideJoin {
    id: number;
    rideId: number;
    userProfileDto: UserProfile;
    creationDateTime: Date;
}