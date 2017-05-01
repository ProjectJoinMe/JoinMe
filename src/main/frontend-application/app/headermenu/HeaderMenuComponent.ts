import {Component, OnInit} from '@angular/core';

import {AppState} from '../AppService';
import {Router} from "@angular/router";
import {SecurityStatus} from "../security/SecurityStatus";
import {SecurityService} from "../security/SecurityService";

@Component({
    selector: 'header-menu',
    providers: [],
    styleUrls: ['./HeaderMenuComponent.css'],
    templateUrl: './HeaderMenuComponent.html'
})
export class HeaderMenuComponent implements OnInit {
    constructor(private router: Router,
                public securityStatus: SecurityStatus,
                public securityService: SecurityService) {
    }

    logout(){
        this.securityService.logout();
    }

    ngOnInit() {
        require('../externalscripts/classie.js');
        require('../externalscripts/borderMenu.js');
    }
}
