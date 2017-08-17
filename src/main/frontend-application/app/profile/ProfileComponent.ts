import {Component} from '@angular/core';
import cloneWith = require("lodash/cloneWith");
import {Validators, FormControl, FormGroup, ReactiveFormsModule, FormBuilder} from "@angular/forms";
import {Http, Response, Headers, URLSearchParams} from "@angular/http";
import {UserProfile} from "./UserProfile";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {SecurityStatus} from "../security/SecurityStatus";

@Component({
    selector: 'profile',
    providers: [],
    styleUrls: ['./ProfileComponent.css'],
    templateUrl: './ProfileComponent.html'
})
export class ProfileComponent {

    userProfile: UserProfile;

    constructor(private http: Http,
                private route: ActivatedRoute,
                private router: Router,
                private securityStatus: SecurityStatus) {
    }

    ngOnInit(): void {
        // TODO change after angular upgrade according to routing tutorial
        this.route.params
            .subscribe((params: Params) => {
                this.getUserProfile(params['username']);
            });
    }

    getUserProfile(username: string): void {
        this.http.get("/api/profile/" + username)
            .subscribe(
                data => {
                    this.userProfile = data.json();
                },
                error => {
                    // TODO error handling
                });
    }
}
