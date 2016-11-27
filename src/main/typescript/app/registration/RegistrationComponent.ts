import { Component } from '@angular/core';
import {RegistrationData} from "./RegistrationData";
import cloneWith = require("lodash/cloneWith");
import {Validators, FormControl, FormGroup, ReactiveFormsModule, FormBuilder} from "@angular/forms";

@Component({
  selector: 'registration',
  providers: [
  ],
  styleUrls: [ './RegistrationComponent.css' ],
  templateUrl: './RegistrationComponent.html'
})
export class RegistrationComponent {

  public registrationForm = this.formBuilder.group({
    username: ["", Validators.required],
    email: ["", Validators.required],
    password: ["", Validators.required],
    passwordCheck: ["", Validators.required],
  });

  constructor(public formBuilder: FormBuilder) {}

  public doRegister() {
    console.log("hallo!");
  }
}
