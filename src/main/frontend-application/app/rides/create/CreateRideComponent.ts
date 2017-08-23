import {Component, OnInit} from "@angular/core";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {WeekDay} from "../WeekDay";
import {Router} from "@angular/router";
import {RideService} from "../../services/RideService";
import {Ride} from "../model/Ride";

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

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private rideService: RideService) {
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
            periodic: [""],
            maxPassengers: ["", Validators.required],
            notes: [""],
            periodicDays: this.formBuilder.array([false, false, false, false, false, false, false])
        });
    }

    public createRide() {
        this.submitted = true;
        if (this.rideForm.valid) {
            console.info("creating ride");
            this.submitDisabled = true;
            let periodicWeekDays: WeekDay[] = [];
            if (<boolean> this.rideForm.get("periodic").value) {
                let periodicDaysControl = <FormArray> this.rideForm.get("periodicDays");
                let weekDayControls: FormControl[] = <FormControl[]> periodicDaysControl.controls;
                periodicWeekDays = weekDayControls.map((weekDayControl, index) => {
                    if (weekDayControl.value) {
                        return <WeekDay>(index + 1);
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
                notes: this.rideForm.get("notes").value
            };
            console.info(rideData);
            this.rideService.createRide(rideData).then(createdRide => {
                this.router.navigate(['/rides', createdRide.id]);
            }).catch(reason => {
                this.submitDisabled = false;
                console.error("failed to create ride, TODO");
            });
        }
    }

    public getPeriodic() {
        return this.rideForm.get("periodic").value;
    }

    public getReturnRide(): boolean {
        return this.rideForm.get("returnRide").value;
    }
}
