import {Routes} from "@angular/router";
import {HomeComponent} from "./home";
import {CreateRideComponent} from "./rides/create";
import {SearchRideComponent} from "./rides/search";
import {AboutComponent} from "./about";
import {NoContentComponent} from "./no-content";
import {RegistrationComponent} from "./registration/RegistrationComponent";
import {LoginComponent} from "./login/LoginComponent";
import {ProfileComponent} from "./profile/ProfileComponent";
import {ProfileEditComponent} from "./profile/edit/ProfileEditComponent";
import {MyRidesComponent} from "./profile/my_rides/MyRidesComponent";
import {RideUpdateComponent} from "./rides/update/RideUpdateComponent";
import {RideDetailsComponent} from "./rides/details/RideDetailsComponent";
import {RideByIdResolver} from "./resolvers/RideByIdResolver";
import {RideJoinsByRideIdResolver} from "./resolvers/RideJoinsByRideIdResolver";
import {UserProfileByUsernameResolver} from "./resolvers/UserProfileByUsernameResolver";
import {MyRidesResolver} from "./resolvers/UserProfileRidesResolver";
import {JoinedRidesComponent} from "./profile/joined_rides/JoinedRidesComponent";
import {JoinedRidesByUserResolver} from "./resolvers/JoinedRidesByUserResolver";
import {PasswordChangeComponent} from "./profile/change_password/PasswordChangeComponent";
import {EmailChangeComponent} from "./profile/change_email/EmailChangeComponent";
import {ProactiveMatchingComponent} from "./profile/proactive_matching/ProactiveMatchingComponent";
import {PointsOfInterestResolver} from "./resolvers/PointsOfInterestResolver";
import {ChatComponent} from "./chat";
import {ChatResolver} from "./resolvers/ChatResolver";


export const ROUTES: Routes = [
    {
        path: '',
        component: HomeComponent
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'register',
        component: RegistrationComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'createRide',
        component: CreateRideComponent
    },
    {
        path: 'searchRide',
        component: SearchRideComponent
    },
    {
        path: 'profile/:username',
        component: ProfileComponent,
        resolve: {userProfile: UserProfileByUsernameResolver}
    },
    {
        path: 'profile/:username/edit',
        component: ProfileEditComponent,
        resolve: {userProfile: UserProfileByUsernameResolver}
    },
    {
        path: 'profile/:username/rides',
        component: MyRidesComponent,
        resolve: {rides: MyRidesResolver}
    },
    {
        path: 'profile/:username/joinedRides',
        component: JoinedRidesComponent,
        resolve: {rides: JoinedRidesByUserResolver}
    },
    {
        path: 'profile/:username/changePassword',
        component: PasswordChangeComponent,
        resolve: {userProfile: UserProfileByUsernameResolver}
    },
    {
        path: 'profile/:username/changeEmail',
        component: EmailChangeComponent,
        resolve: {userProfile: UserProfileByUsernameResolver}
    },
    {
        path: 'profile/:username/proactiveMatchingConfiguration',
        component: ProactiveMatchingComponent,
        resolve: {pois: PointsOfInterestResolver}
    },
    {
        path: 'rides/:id',
        component: RideDetailsComponent,
        resolve: {
            ride: RideByIdResolver,
            rideJoins: RideJoinsByRideIdResolver
        }
    },
    {
        path: 'rides/:id/update',
        component: RideUpdateComponent,
        resolve: {ride: RideByIdResolver}
    },
    {
        path: 'chat/:username',
        component: ChatComponent,
        resolve: {chatMessages: ChatResolver}
    },
    {
        path: 'about',
        component: AboutComponent
    },
    {
        path: '**',
        component: NoContentComponent
    },
];
