import { Component } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {Http, URLSearchParams} from "@angular/http";
import {SearchRequest} from "./SearchRequest";
import {Ride} from "../create/Ride";

@Component({
    selector: 'searchRide',
    providers: [],
    styleUrls: ['./SearchRideComponent.css'],
    templateUrl: './SearchRideComponent.html'

})
export class SearchRideComponent {

    public searchForm: FormGroup;
    public maxDateOfFilter: Date = new Date();
    public submitted: boolean = false;
    public submitDisabled: boolean = false;
    public rides: Ride[];

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private http: Http) {
    }

    public search() {
        this.submitted = true;
        if (this.searchForm.valid) {
            this.submitDisabled = true;
            let searchRequest: SearchRequest = {
                start: <string> this.searchForm.get("start").value,
                destination: <string> this.searchForm.get("destination").value,
                date: new Date(this.searchForm.get("date").value)
            };
            let data = new URLSearchParams();
            data.append('start', searchRequest.start || null);
            data.append('destination', searchRequest.destination || null);
            data.append('date', searchRequest.date ? searchRequest.date.toJSON() : null);
            this.http.get("api/rides/search", {
                search: data
            })
                .subscribe(
                    data => {
                        this.submitDisabled = false;
                        this.rides = data.json();
                    },
                    error => {
                        this.submitDisabled = false;
                        console.error("failed to search rides, TODO");
                    });
            // .catch(this.handleError) // TODO handle all unhandled errors generally by showing some message bar
        }
    }

    ngOnInit() {
        this.searchForm = this.formBuilder.group({
            start: ["",],
            destination: ["",],
            date: ["",],
        });
    }

    goToDetails(ride: Ride) {
        this.router.navigate(['/rides', ride.id]);
    }

}
