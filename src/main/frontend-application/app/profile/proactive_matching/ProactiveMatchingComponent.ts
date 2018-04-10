import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {SecurityService} from ".../security/SecurityService";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserProfileService} from "../../services/UserProfileService";
import {LatLng} from "../../rides/model/LatLng";
import {MapsAutocompletePlaceComponent} from "../../maps/autocomplete/MapsAutocompletePlaceComponent";
import {PointOfInterest} from "./PointOfInterest";

/**
 * Created by Nicole August 2017.
 */

@Component({
    selector: 'edit',
    providers: [],
    styleUrls: ['./ProactiveMatchingComponent.css'],
    templateUrl: './ProactiveMatchingComponent.html'
})
export class ProactiveMatchingComponent implements OnInit {
    @ViewChild('profilePictureFileInput') profilePictureEl: ElementRef;
    @ViewChild('carPictureFileInput') carPictureEl: ElementRef;
    @ViewChild(MapsAutocompletePlaceComponent) placeComponent: MapsAutocompletePlaceComponent;
    public submitted: boolean = false;
    private startLocation: LatLng = null;
    private onPlaceChange: (place: any, isFullPlace: boolean) => void;
    private placeForm: FormGroup;
    private pois: PointOfInterest[] = [];

    constructor(private http: Http,
                private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private router: Router,
                private userProfileService: UserProfileService) {
        this.onPlaceChange = (place: any, isFullPlace: boolean) => {
            if (isFullPlace) {
                this.add(place);
            } else {

            }
        };
    }

    ngOnInit() {
        this.route.data
            .subscribe((data: { pois: PointOfInterest[] }) => {
                this.pois = data.pois;
            });
        this.placeForm = this.formBuilder.group({
            poiMapsInput: ["",],
            poiMapsInputPlaceId: ["", [this.requiredWhenFieldNotEmpty]],
        });
    }

    public add(place: any) {
        this.submitted = true;
        if (this.placeForm.valid) {
            let poi = new PointOfInterest();
            poi.name = <string> this.placeForm.get("poiMapsInput").value;
            poi.placeId = <string> this.placeForm.get("poiMapsInputPlaceId").value;
            let location = place.geometry.location;
            poi.location = this.mapsLatLngToJoinMeLatLng(location);
            this.pois.push(poi);
            this.placeComponent.clear();
            this.update();
            this.submitted = false;
        }
    }

    public update() {
        this.userProfileService.updatePointsOfInterest(this.pois);
        console.log(this.pois);
    }

    private mapsLatLngToJoinMeLatLng(location: any): LatLng {
        return {
            lat: location.lat(),
            lng: location.lng()
        };
    }

    private requiredWhenFieldNotEmpty(control: AbstractControl): any {
        let inputControl = control.root.get("poiMapsInput");
        return (inputControl && inputControl.value) ? Validators.required(control) : null;
    }

    private deletePoi(poi: PointOfInterest) {
        let index = this.pois.findIndex(value => value.placeId === poi.placeId);
        this.pois.splice(index, 1);
        this.update();
    }
}