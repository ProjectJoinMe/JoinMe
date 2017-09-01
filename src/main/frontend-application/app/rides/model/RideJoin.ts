import {UserProfile} from "../../profile/UserProfile";
import {Rating} from "../../ratings/Rating";

export interface RideJoin {
    id: number;
    rideId: number;
    userProfileDto: UserProfile;
    creationDateTime: Date;
    rating: Rating;
}