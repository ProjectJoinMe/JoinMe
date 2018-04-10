/**
 * Created by Nicole August 2017.
 */
import {Component, Input} from "@angular/core";
import {LoginData} from "./LoginData";
import {SecurityService} from "../security/SecurityService";
import {MapsApiKeyService} from "../config/MapsApiKeyService";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";

@Component({
    selector: 'maps-display-embedded-route',
    providers: [],
    styleUrls: ['./MapsDisplayEmbeddedRouteComponent.css'],
    templateUrl: './MapsDisplayEmbeddedRouteComponent.html'
})
export class MapsDisplayEmbeddedRouteComponent {

    @Input() originPlaceId: string;
    @Input() destinationPlaceId: string;
    embeddedRouteMapUrl: SafeResourceUrl;

    constructor(public mapsApiKeyService: MapsApiKeyService,
                public domSanitizer: DomSanitizer) {
        this.determineUrl();
    }

    ngOnChanges(changes: any) {
        if (changes.originPlaceId || changes.destinationPlaceId) {
            this.determineUrl();
        }
    }

    private determineUrl() {
        this.embeddedRouteMapUrl = this.domSanitizer.bypassSecurityTrustResourceUrl(
            'https://www.google.com/maps/embed/v1/directions'
            + '?key=' + this.mapsApiKeyService.mapsApiKey
            + '&origin=place_id:' + this.originPlaceId
            + '&destination=place_id:' + this.destinationPlaceId
            + '&mode=driving'
            + '&units=metric'
        )
    }

}
