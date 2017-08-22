import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component, OnInit} from "@angular/core";
import {SecurityService} from ".../security/SecurityService";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MailValidator} from "../../../validators/MailValidator";

/**
 * Created by Alexander on 22.08.2017.
 */

@Component({
    selector: 'changeEmail',
    providers: [],
    styleUrls: ['./EmailChangeComponent.css'],
    templateUrl: './EmailChangeComponent.html'
})
export class EmailChangeComponent implements OnInit {

    public emailForm: FormGroup;
    public submitted: boolean = false;
    public submitDisabled: boolean = false;

    constructor(private http: Http,
                private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private router: Router) {

    }

    ngOnInit(): void {
        this.emailForm = this.formBuilder.group({
            email: ["", [Validators.required, MailValidator.mailValidator()]],
            // email: ["", [Validators.required, MailValidator.mailValidator()], this.validateUniqueEmailPromise],
            password: ["", [Validators.required]],
        });
    }

    public change() {

    }

}