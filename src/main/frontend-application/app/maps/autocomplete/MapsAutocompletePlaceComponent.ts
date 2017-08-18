import {ChangeDetectorRef, Component, Input, KeyValueDiffer, KeyValueDiffers, NgZone} from '@angular/core';
import {LoginData} from "./LoginData";
import {SecurityService} from "../security/SecurityService";
import {FormGroup} from "@angular/forms";

declare let google: any;

@Component({
    selector: 'maps-autocomplete-place',
    providers: [],
    styleUrls: ['./MapsAutocompletePlaceComponent.css'],
    templateUrl: './MapsAutocompletePlaceComponent.html'
})
export class MapsAutocompletePlaceComponent {

    @Input() name: string;
    @Input() formGroup: FormGroup;
    autocomplete: any;

    constructor(private ngZone: NgZone) {
    }

    ngAfterViewInit() {
        let input = document.getElementById('maps-autocomplete-textfield-' + this.name);
        let options = {};

        this.autocomplete = new google.maps.places.Autocomplete(input, options);

        this.autocomplete.addListener('place_changed', () => {
            // update in context of angular as this event is triggered by google library
            this.ngZone.run(() => {
                this.selectedPlaceWasChanged();
            });
        });
    }

    onPlaceTextChanged() {
        this.setPlaceId(null);
    }

    private selectedPlaceWasChanged() {
        let place = this.autocomplete.getPlace();
        let placeId = place.place_id;
        this.setPlaceId(placeId);
        if (placeId){
            // update model value as textfield value was changed directly by google library
            this.setFormValue(this.name, place.name);
        }
    }

    private setPlaceId(placeId: string) {
        this.setFormValue(this.getPlaceIdFormControlName(), placeId);
    }

    private setFormValue(name: string, value: string) {
        let patch = {};
        patch[name] = value;
        this.formGroup.patchValue(patch);
    }

    private getPlaceIdFormControlName() {
        return this.name + "PlaceId";
    }
}