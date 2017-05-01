/*
 * Angular 2 decorators and services
 */
import {Component, ViewEncapsulation, OnInit} from '@angular/core';
import {AppState} from './AppService';
import {SecurityService} from "./security/SecurityService";

require('./externalscripts/modernizr.custom.js');

/*
 * App Component
 * Top Level Component
 */
@Component({
    selector: 'app',
    encapsulation: ViewEncapsulation.None,
    styleUrls: [
        './generalcss/normalize.css',
        './generalcss/demo.css',
        './generalcss/icons.css',
        './AppComponent.css'
    ],
    templateUrl: "./AppComponent.html"
})
export class AppComponent implements OnInit {
    name = 'JoinMe';
    url = 'https://joinme.io';

    constructor(public appState: AppState,
                private securityService: SecurityService) {

    }

    ngOnInit() {
        console.log('Initializing');
        this.securityService.initialize();
    }

}

/*
 * Please review the https://github.com/AngularClass/angular2-examples/ repo for
 * more angular app examples that you may copy/paste
 * (The examples may not be updated as quickly. Please open an issue on github for us to update it)
 * For help or questions please contact us at @AngularClass on twitter
 * or our chat on Slack at https://AngularClass.com/slack-join
 */
