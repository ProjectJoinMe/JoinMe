import {ApplicationRef, LOCALE_ID, NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule, JsonpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {createInputTransfer, createNewHosts, removeNgStyles} from "@angularclass/hmr";
import {DatePipe} from "@angular/common";
/*
 * Platform and Environment providers/directives/pipes
 */
import {ENV_PROVIDERS} from "./environment";
import {ROUTES} from "./AppRoutes";
// App is our top level component
import {AppComponent} from "./AppComponent";
import {APP_RESOLVER_PROVIDERS} from "./AppResolver";
import {AppState, InternalStateType} from "./AppService";
import {HomeComponent} from "./home";
import {AboutComponent} from "./about";
import {NoContentComponent} from "./no-content";
import {RegistrationComponent} from "./registration/RegistrationComponent";
import {LoginComponent} from "./login/LoginComponent";
import {CreateRideComponent} from "./rides/create/CreateRideComponent";
import {HeaderMenuComponent} from "./headermenu/HeaderMenuComponent";
import {ProfileComponent} from "./profile/ProfileComponent";
import {ProfileEditComponent} from "./profile/edit/ProfileEditComponent";
import {MyRidesComponent} from "./profile/my_rides/MyRidesComponent";
import {RideUpdateComponent} from "./rides/update/RideUpdateComponent";
import {SecurityService} from "./security/SecurityService";
import {SecurityStatus} from "./security/SecurityStatus";
import {SearchRideComponent} from "./rides/search/SearchRideComponent";
import {RideDetailsComponent} from "./rides/details/RideDetailsComponent";
import {TimezonifyDatePipe} from "./util/time/TimezonifyDatePipe";
import {MapsAutocompletePlaceComponent} from "./maps/autocomplete/MapsAutocompletePlaceComponent";
import {RideService} from "./services/RideService";
import {RideJoinsByRideIdResolver} from "./resolvers/RideJoinsByRideIdResolver";
import {RideByIdResolver} from "./resolvers/RideByIdResolver";
import {UserRegistrationService} from "./services/UserRegistrationService";
import {UserProfileByUsernameResolver} from "./resolvers/UserProfileByUsernameResolver";
import {UserProfileService} from "./services/UserProfileService";
import {MyRidesResolver} from "./resolvers/UserProfileRidesResolver";
import {JoinedRidesComponent} from "./profile/joined_rides/JoinedRidesComponent";
import {JoinedRidesByUserResolver} from "./resolvers/JoinedRidesByUserResolver";
import {PasswordChangeComponent} from "./profile/change_password/PasswordChangeComponent";
import {EmailChangeComponent} from "./profile/change_email/EmailChangeComponent";
import {MapsApiKeyService} from "./maps/config/MapsApiKeyService";
import {MapsDisplayEmbeddedRouteComponent} from "./maps/display_embed_route/MapsDisplayEmbeddedRouteComponent";
import {MapsDisplayCustomizableRouteComponent} from "./maps/display_customizable_route/MapsDisplayCustomizableRouteComponent";
import {MessageComponent} from "./message/MessageComponent";
import {Message} from "./message_service/Message";
import {MessageService} from "./message_service/MessageService";
import {NotificationsComponent} from "./notifications/NotificationsComponent";
import {NotificationsService} from "./notifications/NotificationsService";
import {NotificationService} from "./services/NotificationService";
import {ProactiveMatchingComponent} from "./profile/proactive_matching/ProactiveMatchingComponent";
import {RatingService} from "./services/RatingService";
import {PointsOfInterestResolver} from "./resolvers/PointsOfInterestResolver";
import {ChatComponent} from "./chat";
import {ChatService} from "./services/ChatService";

export class MobileDetector {
    // http://detectmobilebrowsers.com/
    static mobile: boolean = (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test((navigator.userAgent || navigator.vendor || (<any>window).opera)) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test((navigator.userAgent || navigator.vendor || (<any>window).opera).substr(0, 4)));
}

console.log("is mobile: " + MobileDetector.mobile);

// Application wide providers
const APP_PROVIDERS = [
    ...APP_RESOLVER_PROVIDERS,
    AppState,
    SecurityService,
    SecurityStatus,
    Message,
    MessageService,
    ChatService
];

type StoreType = {
    state: InternalStateType,
    restoreInputValues: () => void,
    disposeOldHosts: () => void
};

/**
 * `AppModule` is the main entry point into Angular2's bootstraping process
 */
@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AppComponent,
        HeaderMenuComponent,
        AboutComponent,
        HomeComponent,
        RegistrationComponent,
        LoginComponent,
        CreateRideComponent,
        SearchRideComponent,
        ProfileComponent,
        RideDetailsComponent,
        MyRidesComponent,
        RideUpdateComponent,
        NoContentComponent,
        TimezonifyDatePipe,
        MapsAutocompletePlaceComponent,
        MapsDisplayCustomizableRouteComponent,
        MapsDisplayEmbeddedRouteComponent,
        ProfileEditComponent,
        JoinedRidesComponent,
        PasswordChangeComponent,
        EmailChangeComponent,
        MessageComponent,
        NotificationsComponent,
        ProactiveMatchingComponent,
        AboutComponent,
        ChatComponent
    ],
    imports: [
        // Angular
        BrowserModule,
        FormsModule,
        HttpModule,
        JsonpModule,
        RouterModule.forRoot(ROUTES, {useHash: true}),
        // reactive
        ReactiveFormsModule,
    ],
    providers: [ // expose our Services and Providers into Angular's dependency injection
        ENV_PROVIDERS,
        APP_PROVIDERS,
        {provide: LOCALE_ID, useValue: "de-DE"},
        MapsApiKeyService,
        DatePipe,
        TimezonifyDatePipe,
        RideService,
        UserRegistrationService,
        UserProfileService,
        RideByIdResolver,
        PointsOfInterestResolver,
        RideJoinsByRideIdResolver,
        UserProfileByUsernameResolver,
        MyRidesResolver,
        JoinedRidesByUserResolver,
        NotificationsService,
        NotificationService,
        RatingService
    ]
})
export class AppModule {
    constructor(public appRef: ApplicationRef, public appState: AppState) {
    }

    hmrOnInit(store: StoreType) {
        if (!store || !store.state) return;
        console.log('HMR store', JSON.stringify(store, null, 2));
        // set state
        this.appState._state = store.state;
        // set input values
        if ('restoreInputValues' in store) {
            let restoreInputValues = store.restoreInputValues;
            setTimeout(restoreInputValues);
        }

        this.appRef.tick();
        delete store.state;
        delete store.restoreInputValues;
    }

    hmrOnDestroy(store: StoreType) {
        const cmpLocation = this.appRef.components.map(cmp => cmp.location.nativeElement);
        // save state
        const state = this.appState._state;
        store.state = state;
        // recreate root elements
        store.disposeOldHosts = createNewHosts(cmpLocation);
        // save input values
        store.restoreInputValues = createInputTransfer();
        // remove styles
        removeNgStyles();
    }

    hmrAfterDestroy(store: StoreType) {
        // display new elements
        store.disposeOldHosts();
        delete store.disposeOldHosts;
    }

}

