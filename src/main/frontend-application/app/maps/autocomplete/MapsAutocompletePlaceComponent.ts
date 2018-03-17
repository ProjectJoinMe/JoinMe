import {Component, Input, NgZone} from "@angular/core";
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
    @Input() onPlaceChange?: (place: any, isFullPlace: boolean) => void;
    autocomplete: any;
    inputFieldElement: any;

    constructor(private ngZone: NgZone) {
    }

    ngAfterViewInit() {
        this.inputFieldElement = document.getElementById('maps-autocomplete-textfield-' + this.name);
        let options = {};

        this.autocomplete = new google.maps.places.Autocomplete(this.inputFieldElement, options);

        this.autocomplete.addListener('place_changed', () => {
            // update in context of angular as this event is triggered by google library
            this.ngZone.run(() => {
                this.selectedPlaceWasChanged();
            });
        });
    }

    onPlaceTextChanged() {
        if (this.formGroup.contains(this.getPlaceIdFormControlName())) {
            this.setPlaceId(null);
            if (this.onPlaceChange) {
                this.onPlaceChange(null, false);
            }
        }
    }

    public clear() {
        this.inputFieldElement.value = "";
        this.setTextFormValue("");
        this.setPlaceId(null);
    }

    private selectedPlaceWasChanged() {
        let place = this.autocomplete.getPlace();
        let placeId = place.place_id;
        if (this.formGroup.contains(this.getPlaceIdFormControlName())) {
            this.setPlaceId(placeId);
        }
        if (placeId && this.formGroup.contains(this.name)) {
            // update model value as textfield value was changed directly by google library
            this.setFormValue(this.name, this.inputFieldElement.value);
        }
        if (this.onPlaceChange) {
            this.onPlaceChange(place, !!placeId);
        }
    }

    private setTextFormValue(value: string) {
        this.setFormValue(this.name, value);
    }

    private setPlaceId(placeId: string) {
        this.setFormValue(this.getPlaceIdFormControlName(), placeId);
    }

    private setFormValue(name: string, value: string) {
        let patch = {};
        patch[name] = value;
        this.formGroup.patchValue(patch);
        this.formGroup.get(name).markAsDirty();
    }

    private getPlaceIdFormControlName() {
        return this.name + "PlaceId";
    }
}
