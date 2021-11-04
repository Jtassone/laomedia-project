import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators'
import { Observable } from 'rxjs';

import { Classification } from './model/classification.model';


@Injectable({
  providedIn: 'root'
})
export class HttpService {

  baseURL = 'https://8jyixjqsxb.execute-api.us-east-1.amazonaws.com/Prod/';

  constructor(private _http: HttpClient) { }

  getClassifications(): Observable<Classification[]> {
    const url = `${this.baseURL}classifications`;
    return this._http.get<Classification[]>(url).pipe(
      tap(data => console.log(`Classifications: ${JSON.stringify(data)}`))
    )
  }

}
