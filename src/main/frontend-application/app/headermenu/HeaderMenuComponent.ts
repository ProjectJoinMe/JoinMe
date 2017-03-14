import {Component, OnInit} from '@angular/core';

import { AppState } from '../AppService';
import {Router} from "@angular/router";

@Component({
  selector: 'header-menu',
  providers: [
  ],
  styleUrls: [ './HeaderMenuComponent.css' ],
  templateUrl: './HeaderMenuComponent.html'
})
export class HeaderMenuComponent implements OnInit {
  constructor(private router: Router) {
  }

  ngOnInit() {
    require('../externalscripts/classie.js');
    require('../externalscripts/borderMenu.js');
  }
}
