import {Headers} from "@angular/http";

export abstract class AbstractApiService {

    protected headers = new Headers({'Content-Type': 'application/json'});

    protected handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO for demo purposes only output
        return Promise.reject(error.message || error);
    }
}