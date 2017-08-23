import {Component, ElementRef, Input, NgZone} from "@angular/core";
import {LoginData} from "./LoginData";
import {SecurityService} from "../security/SecurityService";

declare let google: any;

/**
 * @unused Currently unused as it used the quota of the Maps Directions API. Embedded maps/routes have unlimited quota but arent that customizable which isnt necessary in our case.
 */
@Component({
    selector: 'maps-display-customizable-route',
    providers: [],
    styleUrls: ['./MapsDisplayCustomizableRouteComponent.css'],
    templateUrl: './MapsDisplayCustomizableRouteComponent.html'
})
export class MapsDisplayCustomizableRouteComponent {

    @Input() originPlaceId: string;
    @Input() destinationPlaceId: string;
    mapElement: any;
    directionsService: any;
    directionsDisplay: any;
    routeLoaded: boolean = false;

    constructor(private ngZone: NgZone,
                private elementRef: ElementRef) {
    }

    ngAfterViewInit() {
        this.mapElement = this.elementRef.nativeElement.getElementsByClassName('map')[0];

        this.directionsService = new google.maps.DirectionsService();
        this.directionsDisplay = new google.maps.DirectionsRenderer();
        let map = new google.maps.Map(this.mapElement, {
            zoom: 7
        });

        this.directionsDisplay.setMap(map);
        this.calculateAndDisplayRoute();

    }

    ngOnChanges(changes: any) {
        if (this.routeLoaded) {
            if (this.originPlaceId && this.destinationPlaceId) {
                this.calculateAndDisplayRoute();
            }
        }
    }

    private calculateAndDisplayRoute() {
        let origin = {
            placeId: this.originPlaceId
        };
        let destination = {
            placeId: this.destinationPlaceId
        };
        this.directionsService.route({
            origin: origin,
            destination: destination,
            travelMode: 'DRIVING',
            provideRouteAlternatives: false,
            unitSystem: 1, // UnitSystem.METRIC
        }, (response, status) => {
            this.ngZone.run(() => {
                if (status === 'OK') {
                    this.displayRoute(response);
                } else {
                    // TODO display error when no route was found or other error
                }
            });
        });
    }

    private displayRoute(response: any) {
        this.directionsDisplay.setDirections(response);
        this.routeLoaded = true;
    }
}
