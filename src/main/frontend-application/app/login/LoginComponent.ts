import {Component} from '@angular/core';
import cloneWith = require("lodash/cloneWith");
import {Validators, FormControl, FormGroup, ReactiveFormsModule, FormBuilder} from "@angular/forms";
import {Http, Response, Headers, URLSearchParams} from "@angular/http";
import {LoginData} from "./LoginData";
import {SecurityService} from "../security/SecurityService";
import {Router} from "@angular/router";

@Component({
    selector: 'login',
    providers: [],
    styleUrls: ['./LoginComponent.css'],
    templateUrl: './LoginComponent.html'
})
export class LoginComponent {

    public loginForm = this.formBuilder.group({
        username: ["", Validators.required],
        password: ["", Validators.required]
    });

    constructor(public formBuilder: FormBuilder,
                private http: Http,
                private securityService: SecurityService,
                private router: Router) {
    }

    public login() {
        let loginData: LoginData = {
            username: <string> this.loginForm.get("username").value,
            password: <string> this.loginForm.get("password").value
        };

        let data = new URLSearchParams();
        data.append('username', loginData.username);
        data.append('password', loginData.password);
        this.http.post("api/login", data)
            .subscribe(
                data => {
                    console.info("logged in successfully!");
                    this.securityService.updateSecurityStatus();
                    this.router.navigate(['/home']);
                    // TODO success -> show confirmation message and move to home screen
                },
                error => {
                    console.error("failed to login, TODO")
                });

    }
}
