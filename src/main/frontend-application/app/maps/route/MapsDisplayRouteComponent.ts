import {
    ChangeDetectorRef, Component, ElementRef, Input, KeyValueDiffer, KeyValueDiffers, NgZone,
    ViewChild
} from '@angular/core';
import {LoginData} from "./LoginData";
import {SecurityService} from "../security/SecurityService";
import {FormGroup} from "@angular/forms";
import {Ride} from "../../rides/create/Ride";

declare let google: any;

@Component({
    selector: 'maps-ride-route',
    providers: [],
    styleUrls: ['./MapsDisplayRouteComponent.css'],
    templateUrl: './MapsDisplayRouteComponent.html'
})
export class MapsDisplayRouteComponent {

    @Input() ride: Ride;
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
            this.calculateAndDisplayRoute();
        }
    }

    calculateAndDisplayRoute() {
        let origin = {
            placeId: this.ride.startPlaceId
        };
        let destination = {
            placeId: this.ride.destinationPlaceId
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
                    this.directionsDisplay.setDirections(response);
                    this.routeLoaded = true;
                } else {
                    // TODO display error when no route was found or other error
                }
            });
        });
    }
}
