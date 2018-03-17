import {Pipe, PipeTransform} from '@angular/core';
import * as moment from 'moment-timezone';

@Pipe({
    name: 'timezonifyDate'
})
export class TimezonifyDatePipe implements PipeTransform {
    transform(value: string | Date): Date | null {
        if (value === undefined) {
            return undefined;
        }
        if (value === null) {
            return null;
        }
        return (<any>moment(value)).add(moment().utcOffset(), 'minutes').toDate();
    }
}