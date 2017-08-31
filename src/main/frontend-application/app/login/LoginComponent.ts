import {Component} from "@angular/core";
import {FormBuilder, Validators} from "@angular/forms";
import {Http, URLSearchParams} from "@angular/http";
import {LoginData} from "./LoginData";
import {SecurityService} from "../security/SecurityService";
import {Router} from "@angular/router";
import cloneWith = require("lodash/cloneWith");
import {MessageService} from "../message_service/MessageService";

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
                private router: Router,
                private messageService: MessageService) {
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
                    this.securityService.updateSecurityStatus();
                    this.router.navigate(['/home']);
                },
                error => {
                    this.messageService.setMessage("Falsche Login-Daten, bitte Eingabe überprüfen.", "failure");
                });

    }
}
