import {Component, OnInit} from "@angular/core";
import {AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {RideService} from "../../services/RideService";
import {Ride} from "../model/Ride";
import {MessageService} from "../../message_service/MessageService";

/**
 * Created by Nicole August 2017.
 */

@Component({
    selector: 'createRide',
    providers: [],
    styleUrls: ['./CreateRideComponent.css'],
    templateUrl: './CreateRideComponent.html'
})
export class CreateRideComponent implements OnInit {

    public rideForm: FormGroup;
    public currentDate: Date = new Date();
    public submitted: boolean = false;
    public submitDisabled: boolean = false;
    private onStartPlaceChange: (place: any, isFullPlace: boolean) => void;
    private onDestinationPlaceChange: (place: any, isFullPlace: boolean) => void;
    private recommendedPrice: number|null = null;

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private rideService: RideService,
                private messageService: MessageService) {
        this.onStartPlaceChange = (place: any, isFullPlace: boolean) => {
            this.updateRouteInfo();
        };
        this.onDestinationPlaceChange = (place: any, isFullPlace: boolean) => {
            this.updateRouteInfo();
        };
    }

    ngOnInit() {
        this.rideForm = this.formBuilder.group({
            start: ["", [Validators.required]],
            startPlaceId: ["", [Validators.required]],
            destination: ["", [Validators.required]],
            destinationPlaceId: ["", [Validators.required]],
            departureDate: ["", [Validators.required]],
            departureHour: ["", Validators.required],
            departureMinute: ["", Validators.required],
            returnRide: [""],
            returnDepartureDate: [""],
            returnDepartureHour: [""],
            returnDepartureMinute: [""],
            maxPassengers: ["", Validators.required],
            notes: [""],
            pricePerPassenger: ["", Validators.required],
            periodic: [""],
            periodicDays: this.formBuilder.array([false, false, false, false, false, false, false], this.periodicDaysValidator),
        });
    }

    public createRide() {
        this.submitted = true;
        if (this.rideForm.valid) {
            console.info("creating ride");
            this.submitDisabled = true;
            let periodicWeekDays: number[] = [];
            if (<boolean> this.rideForm.get("periodic").value) {
                let periodicDaysControl = <FormArray> this.rideForm.get("periodicDays");
                let weekDayControls: FormControl[] = <FormControl[]> periodicDaysControl.controls;
                periodicWeekDays = weekDayControls.map((weekDayControl, index) => {
                    if (weekDayControl.value) {
                        return index + 1;
                    } else {
                        return null;
                    }
                }).filter((index) => index !== null);
            }
            let departureDate = new Date(this.rideForm.get("departureDate").value);
            let departureHour = <number> this.rideForm.get("departureHour").value;
            let departureMinute = <number> this.rideForm.get("departureMinute").value;
            let returnDepartureDate = new Date(this.rideForm.get("returnDepartureDate").value);
            let returnDepartureHour = <number> this.rideForm.get("returnDepartureHour").value;
            let returnDepartureMinute = <number> this.rideForm.get("returnDepartureMinute").value;

            let rideData: Ride = {
                start: <string> this.rideForm.get("start").value,
                startPlaceId: <string> this.rideForm.get("startPlaceId").value,
                destination: <string> this.rideForm.get("destination").value,
                destinationPlaceId: <string> this.rideForm.get("destinationPlaceId").value,
                departureDateTime: new Date(departureDate.getFullYear(), departureDate.getMonth(), departureDate.getDate(), departureHour, departureMinute, 0, 0),
                returnDepartureDateTime: this.getReturnRide() ? new Date(returnDepartureDate.getFullYear(), returnDepartureDate.getMonth(), returnDepartureDate.getDate(), returnDepartureHour, returnDepartureMinute, 0, 0) : null,
                maxPassengers: <number> this.rideForm.get("maxPassengers").value,
                notes: this.rideForm.get("notes").value,
                pricePerPassenger: this.rideForm.get("pricePerPassenger").value,
                periodic: <boolean> this.rideForm.get("periodic").value,
                periodicWeekDays: periodicWeekDays
            };
            this.rideService.createRide(rideData).then(createdRide => {
                this.messageService.setMessage("Ihre Fahrt wurde erfolgreich erstellt.", "success");
                this.router.navigate(['/rides', createdRide.id]);
            }).catch(reason => {
                this.submitDisabled = false;
                this.messageService.setMessage("Fahrt konnte nicht erstellt werden.", "failure");
            });
        }
    }

    public getPeriodic() {
        return this.rideForm.get("periodic").value;
    }

    public getReturnRide(): boolean {
        return this.rideForm.get("returnRide").value;
    }

    private periodicDaysValidator(control: AbstractControl): any {
        let periodicControl = control.root.get("periodic");
        if (periodicControl && (!periodicControl.value)) {
            return null;
        } else {
            for (let i = 0; i < control.value.length; i++) {
                if (control.value[i]) {
                    return null;
                }
            }
            return {
                periodicDaysInvalid: {
                    valid: false
                }
            };
        }
    }

    private updateRouteInfo() {
        let startPlaceId = <string> this.rideForm.get("startPlaceId").value;
        let destinationPlaceId = <string> this.rideForm.get("destinationPlaceId").value;
        if (startPlaceId && destinationPlaceId) {
            this.rideService.getRouteInfo({
                startPlaceId: startPlaceId,
                destinationPlaceId: destinationPlaceId
            }).then(rideWithRouteInformation => {
                this.recommendedPrice = rideWithRouteInformation.route.suggestedPricePerPassenger;
            }).catch(reason => {
                console.error("failed to get ride information, TODO");
            });
        }
    }
}
