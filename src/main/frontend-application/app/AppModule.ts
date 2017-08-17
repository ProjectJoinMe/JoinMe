import {NgModule, ApplicationRef, LOCALE_ID} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule, JsonpModule} from '@angular/http';
import {RouterModule} from '@angular/router';
import {removeNgStyles, createNewHosts, createInputTransfer} from '@angularclass/hmr';
import {DatePipe} from '@angular/common';

/*
 * Platform and Environment providers/directives/pipes
 */
import {ENV_PROVIDERS} from './environment';
import {ROUTES} from './AppRoutes';
// App is our top level component
import {AppComponent} from './AppComponent';
import {APP_RESOLVER_PROVIDERS} from './AppResolver';
import {AppState, InternalStateType} from './AppService';
import {HomeComponent} from './home';
import {AboutComponent} from './about';
import {NoContentComponent} from './no-content';
import {RegistrationComponent} from "./registration/RegistrationComponent";
import {LoginComponent} from "./login/LoginComponent";
import {CreateRideComponent} from "./rides/create/CreateRideComponent";
import {HeaderMenuComponent} from "./headermenu/HeaderMenuComponent";
import {ProfileComponent} from "./profile/ProfileComponent";
import {MyRidesComponent} from "./profile/rides/MyRidesComponent";
import {RideUpdateComponent} from "./rides/update/RideUpdateComponent";
import {SecurityService} from "./security/SecurityService";
import {SecurityStatus} from "./security/SecurityStatus";
import {SearchRideComponent} from "./rides/search/SearchRideComponent";
import {DetailComponent} from "./+detail/detail.component";
import {RideDetailsComponent} from "./rides/details/RideDetailsComponent";
import {TimezonifyDatePipe} from "./util/time/TimezonifyDatePipe";
import {MapsAutocompletePlaceComponent} from "./maps/autocomplete/MapsAutocompletePlaceComponent";

// Application wide providers
const APP_PROVIDERS = [
    ...APP_RESOLVER_PROVIDERS,
    AppState,
    SecurityService,
    SecurityStatus
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
        MapsAutocompletePlaceComponent
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
        { provide: LOCALE_ID, useValue: "de-DE" },
        DatePipe,
        TimezonifyDatePipe
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

