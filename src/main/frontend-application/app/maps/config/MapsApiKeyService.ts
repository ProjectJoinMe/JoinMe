import {Injectable} from "@angular/core";
import {LoginData} from "./LoginData";
import {SecurityService} from "../security/SecurityService";

@Injectable()
export class MapsApiKeyService {
    // located in index.html, GoogleMapsClientConfiguration and this file
    public mapsApiKey: string = "AIzaSyBFSDX0w4PcRM536-oUFTl7TTTOqSASy70";
}
