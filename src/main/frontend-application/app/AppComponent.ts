/*
 * Angular 2 decorators and services
 */
import {Component, ElementRef, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {AppState} from './AppService';
import {SecurityService} from "./security/SecurityService";
import {SecurityStatus} from "./security/SecurityStatus";
import {Router} from "@angular/router";
import {NotificationsService} from "./notifications/NotificationsService";

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
        './AppComponent.css',
        './AppComponentMenu.css'
    ],
    templateUrl: "./AppComponent.html",
    host: {
        '(document:click)': 'onClick($event)',
    }
})
export class AppComponent implements OnInit {
    name = 'JoinMe';
    url = 'https://joinme.io';
    notificationsShown: boolean = false;
    @ViewChild('notificationsElementContainer') notificationsElementContainer: ElementRef;

    constructor(public appState: AppState,
                private securityService: SecurityService,
                private router: Router,
                public securityStatus: SecurityStatus,
                public notificationsService: NotificationsService,
                private appComponentElement: ElementRef) {
    }

    logout() {
        this.securityService.logout();
    }

    ngOnInit() {
        this.securityService.initialize();
        require('./externalscripts/classie.js');
        require('./externalscripts/borderMenu.js');
    }

    myFunction() {
        var x = document.getElementById("myTopnav");
        if (x.className === "topnav") {
            x.className += " responsive";
        } else {
            x.className = "topnav";
        }
    }

    hideMenu() {
        var menu = document.getElementById("myTopnav");
        if (menu.className !== "topnav") {
            menu.className = "topnav";
        }
    }

    toggleNotifications() {
        this.notificationsShown = !this.notificationsShown;
        if (!this.notificationsShown) {
            this.notificationsService.markNotificationsAsRead();
        }
    }

    onClick(event) {
        if (this.notificationsShown) {
            let toggleNotificationsButton = document.getElementById('toggleNotificationsButton');
            if (!this.notificationsElementContainer.nativeElement.contains(event.target)
                && !toggleNotificationsButton.contains(event.target)) {
                this.toggleNotifications();
            }
        }
    }
}


/*
 * Please review the https://github.com/AngularClass/angular2-examples/ repo for
 * more angular app examples that you may copy/paste
 * (The examples may not be updated as quickly. Please open an issue on github for us to update it)
 * For help or questions please contact us at @AngularClass on twitter
 * or our chat on Slack at https://AngularClass.com/slack-join
 */

