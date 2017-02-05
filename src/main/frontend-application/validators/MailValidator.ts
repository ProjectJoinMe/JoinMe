import {ValidatorFn, Validators} from "@angular/forms";

export class MailValidator{

    static mailValidator(): ValidatorFn {
        return Validators.pattern('^[a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,15})$');
    }

}