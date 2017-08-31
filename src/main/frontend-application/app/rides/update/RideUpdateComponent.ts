import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {TimezonifyDatePipe} from "../../util/time/TimezonifyDatePipe";
import {RideService} from "../../services/RideService";
import {Ride} from "../model/Ride";
import {MessageService} from "../../message_service/MessageService";

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
                    pricePerPassenger: this.ride.pricePerPassenger
                });
            });
    }

    public updateRide() {
        this.submitted = true;
        if (this.rideForm.valid) {
            console.info("updating ride");
            this.submitDisabled = true;
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
                pricePerPassenger: this.rideForm.get("pricePerPassenger").value
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

    public getReturnRide() {
        return this.rideForm.get("returnRide").value;
    }

    private getDateComponent(date: Date, format: string) {
        return date !== null
            ? this.datePipe.transform(date, format)
            : null;
    }

    goToDetails(ride: Ride) {
        this.router.navigate(['/rides', ride.id]);
    }

}
