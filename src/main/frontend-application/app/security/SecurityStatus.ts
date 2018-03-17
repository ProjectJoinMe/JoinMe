import {Injectable} from "@angular/core";

@Injectable()
export class SecurityStatus {
    public loggedIn: boolean;
    public username: string;
}