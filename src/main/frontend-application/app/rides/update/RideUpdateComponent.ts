import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {TimezonifyDatePipe} from "../../util/time/TimezonifyDatePipe";
import {RideService} from "../../services/RideService";
import {Ride} from "../model/Ride";
import {MessageService} from "../../message_service/MessageService";

/**
 * Created by Nicole August 2017.
 */

@Component({
    selector: 'myRidesUpdate',
    providers: [],
    styleUrls: ['./RideUpdateComponent.css'],
    templateUrl: './RideUpdateComponent.html'
})
export class RideUpdateComponent implements OnInit {

    public ride: Ride;
    public rideForm: FormGroup;
    public currentDate: Date = new Date();
    public submitted: boolean = false;
    public submitDisabled: boolean = false;

    constructor(private rideService: RideService,
                private formBuilder: FormBuilder,
                private route: ActivatedRoute,
                private router: Router,
                private datePipe: DatePipe,
                private timezonifyDatePipe: TimezonifyDatePipe,
                private messageService: MessageService) {
    }

    ngOnInit(): void {
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

        this.route.data
            .subscribe((data: { ride: Ride }) => {
                this.ride = data.ride;

                let departureDateTime = this.timezonifyDatePipe.transform(this.ride.departureDateTime);
                let returnDepartureDateTime = this.timezonifyDatePipe.transform(this.ride.returnDepartureDateTime);
                this.rideForm.setValue({
                    start: this.ride.start,
                    startPlaceId: this.ride.startPlaceId,
                    destination: this.ride.destination,
                    destinationPlaceId: this.ride.destinationPlaceId,
                    departureDate: this.datePipe.transform(departureDateTime, 'yyyy-MM-dd'),
                    departureHour: this.datePipe.transform(departureDateTime, 'HH'),
                    departureMinute: this.datePipe.transform(departureDateTime, 'mm'),
                    returnRide: returnDepartureDateTime !== null,
                    returnDepartureDate: this.getDateComponent(returnDepartureDateTime, 'yyyy-MM-dd'),
                    returnDepartureHour: this.getDateComponent(returnDepartureDateTime, 'HH'),
                    returnDepartureMinute: this.getDateComponent(returnDepartureDateTime, 'mm'),
                    maxPassengers: this.ride.maxPassengers,
                    notes: this.ride.notes,
                    pricePerPassenger: this.ride.pricePerPassenger,
                    periodic: this.ride.periodic,
                    periodicDays: this.periodicDaysListToFormBooleanList()
                });
            });
    }

    public updateRide() {
        this.submitted = true;
        if (this.rideForm.valid) {
            console.info("updating ride");
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
                id: this.ride.id,
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
            console.info(rideData);

            this.rideService.updateRide(rideData).then(updatedRide => {
                this.messageService.setMessage("Fahrt wurde erfolgreich bearbeitet.", "success");
                this.router.navigate(['/rides', this.ride.id]);
            }).catch(reason => {
                this.submitDisabled = false;
                this.messageService.setMessage("Fahrt konnte nicht bearbeitet werden.", "failure");
            });
        }
    }

    public getPeriodic() {
        return this.rideForm.get("periodic").value;
    }

    public getReturnRide() {
        return this.rideForm.get("returnRide").value;
    }

    goToDetails(ride: Ride) {
        this.router.navigate(['/rides', ride.id]);
    }

    private periodicDaysListToFormBooleanList() {
        if (!this.ride.periodicWeekDays) {
            return [false, false, false, false, false, false, false];
        } else {
            return [
                this.ride.periodicWeekDays.find(value => value === 1) !== undefined,
                this.ride.periodicWeekDays.find(value => value === 2) !== undefined,
                this.ride.periodicWeekDays.find(value => value === 3) !== undefined,
                this.ride.periodicWeekDays.find(value => value === 4) !== undefined,
                this.ride.periodicWeekDays.find(value => value === 5) !== undefined,
                this.ride.periodicWeekDays.find(value => value === 6) !== undefined,
                this.ride.periodicWeekDays.find(value => value === 7) !== undefined
            ];
        }
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

    private getDateComponent(date: Date, format: string) {
        return date !== null
            ? this.datePipe.transform(date, format)
            : null;
    }

}
