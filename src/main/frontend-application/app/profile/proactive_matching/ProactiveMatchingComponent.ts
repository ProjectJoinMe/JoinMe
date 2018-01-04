import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {UserProfile} from "../UserProfile";
import {SecurityService} from ".../security/SecurityService";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserProfileService} from "../../services/UserProfileService";
import {MessageService} from "../../message_service/MessageService";
import {LatLng} from "../../rides/model/LatLng";
import {MapsAutocompletePlaceComponent} from "../../maps/autocomplete/MapsAutocompletePlaceComponent";
import {PointOfInterest} from "./PointOfInterest";

/**
 * Created by Alexander on 18.08.2017.
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

    private startLocation: LatLng = null;
    private onPlaceChange: (place: any, isFullPlace: boolean) => void;
    private placeForm: FormGroup;
    public submitted: boolean = false;
    private pois: PointOfInterest[] = [];

    constructor(private http: Http,
                private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private router: Router,
                private userProfileService: UserProfileService,
                private messageService: MessageService) {
        this.onPlaceChange = (place: any, isFullPlace: boolean) => {
            if (isFullPlace) {
                this.add();
            } else {

            }
        };
    }

    ngOnInit() {
        this.placeForm = this.formBuilder.group({
            poiMapsInput: ["",],
            poiMapsInputPlaceId: ["", [this.requiredWhenFieldNotEmpty]],
        });
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

    public add() {
        this.submitted = true;
        if (this.placeForm.valid) {
            let poi = new PointOfInterest();
            poi.name = <string> this.placeForm.get("poiMapsInput").value;
            poi.placeId = <string> this.placeForm.get("poiMapsInputPlaceId").value;
            this.pois.push(poi);
            this.placeComponent.clear();
        }
    }

    private deletePoi(poi: PointOfInterest){
        let index = this.pois.findIndex(value => value.placeId === poi.placeId);
        this.pois.splice(index, 1);
    }
}