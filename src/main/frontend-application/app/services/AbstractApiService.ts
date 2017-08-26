import {Headers} from "@angular/http";

export abstract class AbstractApiService {

    protected headers = new Headers({'Content-Type': 'application/json'});
    protected fileUploadHeaders = new Headers({'Content-Type': 'multipart/form-data;boundary=gc0p4Jq0M2Yt08jU534c0p'});

    protected handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO for demo purposes only output
        return Promise.reject(error.message || error);
    }
}