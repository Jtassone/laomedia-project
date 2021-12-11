import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, tap } from 'rxjs/operators'
import { Observable } from 'rxjs';

import { Classification } from './model/classification.model';
import { Algorithm2 } from './model/algorithm.model';
import Implementation from './model/implementation.model';



@Injectable({
  providedIn: 'root'
})
export class HttpService {

  baseURL = 'https://8jyixjqsxb.execute-api.us-east-1.amazonaws.com/Prod/';

  urls = {
    get: {
      algos: "https://jsp4qhazd6.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-getAlgorithms",
      class: "https://idxllinx9l.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-getClassification",
      imp: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-getImplementation"
    }, post: {
      algos: "https://lnpmfr4e8l.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-postAlgorithm",
      class: "https://6jj1ay30h6.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-postClassification",
      imp: "https://h1l85l4gp8.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-postImplementation",
      classMerge: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/classifications/merge",
    }, delete: {
      class: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/classifications/",
    }
  }

  constructor(private _http: HttpClient) { }

  getClassifications(): Observable<Classification[]> {
    // const url = `${this.baseURL}classifications`;
    const url = this.urls.get['class'];
    return this._http.get<Classification[]>(url).pipe(
      tap(data => console.log(`Classifications: ${JSON.stringify(data)}`))
    )
  }

  getClassification(id: string): Observable<Classification> {
    // const url = `${this.baseURL}classification/${id}`;
    const url = this.urls.get['class'];
    return this._http.get<Classification>(url).pipe(
      tap(data => console.log(`Data: ${JSON.stringify(data)}`))
    )
  }

  addClassification(name: string, parent: string): Observable<Classification> {
    const url = this.urls.post['class'];
    const body = JSON.stringify({name: name, parentClassificationId: parent});
    return this._http.post<Classification>(url, body).pipe(
      tap(data => console.log(`Data from Hello World: ${JSON.stringify(data)}`))
    );
  }

  deleteClassification(id: string): Observable<any> {
    const url = this.urls.delete['class'] + id;
    return this._http.delete<any>(url).pipe(
      tap(data => console.log(`Data from Delete: ${JSON.stringify(data)}`))
    )
  }

  mergeClassifications(name: string, id1: string, id2: string) {
    const body = {
      newClassificationName: name,
      classification1Id: id1,
      classification2Id: id2,
    }
    const url = this.urls.post.classMerge;
    return this._http.post<any>(url, body).pipe(
      tap(data => console.log(`Merge status: ${JSON.stringify(data)}`))
    );
  }

  getAlgorithms(): Observable<Algorithm2[]> {
    const url = this.urls.get['algos'];
    return this._http.get<Algorithm2[]>(url).pipe(
      tap(data => console.log(`List of All Algorithms: ${JSON.stringify(data)}`))
    )
  }

  addAlgorithm(name: string, classificationId: string, algorithmDetails: string): Observable<any> {
    const url = this.urls.post['algos'];
    const body = {
      name,
      classificationId,
      algorithmDetails
    }
    return this._http.post<any>(url, body).pipe(
      tap(data => console.log(`List of All Algorithms: ${JSON.stringify(data)}`))
    );
  }

  getImplementations(): Observable<Implementation[]> {
    const url = this.urls.get['imp'];
    return this._http.get<Implementation[]>(url).pipe(
      tap(data => console.log(`List of All Implementations: ${JSON.stringify(data)}`)),
      map(data => {
        data.map(imp => {
          imp.implementationDetails = atob(imp.implementationDetails)
        })
        return data;
      })
    );
  }

  addImplementation(name: string, algorithmId: string, implementationDetails: string): Observable<any> {
    const url = this.urls.post['imp'];
    const body = {
      name,
      algorithmId,
      implementationDetails
    }
    return this._http.post<any>(url, body).pipe(
      tap(data => console.log(`Implementation added: ${JSON.stringify(data)}`))
    );
  }

  helloWorld(): any {
    // const url = `${this.baseURL}`;
    const url = 'https://jsp4qhazd6.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-getAlgorithms'
    return this._http.get(url).pipe(
      tap(data => console.log(`Data from Hello World: ${JSON.stringify(data)}`))
    );
  }

  postTest(): any {
    return null;
    const mode: string = "post";
    if (mode === "get") {
      const getURLs = {
        algos: "https://jsp4qhazd6.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-getAlgorithms",
        class: "https://idxllinx9l.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-getClassification",
        imp: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-getImplementation"
      }
      return this._http.get(getURLs['algos']).pipe(
        tap(data => console.log(`Data from Hello World: ${JSON.stringify(data)}`))
      );
    }
    if (mode === "post") {
      const postURLs = {
        algos: "https://lnpmfr4e8l.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-postAlgorithm",
        class: "https://6jj1ay30h6.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-postClassification",
        imp: " https://h1l85l4gp8.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-postImplementation",
      }

      const postBodies = {
        algos: {
            "name":"angular algo test unstring",
            "algorithmDetails":"This algorithm was submitted from the Angular frontend. It should be number eight.",
            "classificationId":"855866ef-e0b7-48c8-be77-c977c4c9a054"
        },
        class: JSON.stringify({
          "name": "Testing classification from Angular frontend",
        }),
        imp: {
          name: "Angular test post implementation zero stringification new message no commas no drama",
          implementationDetails: "Angular test post implementation, stringified",
          algorithmId: "220c8b30-8e07-38c9-87d2-cdf6c39e00da"
        }
      }
      const use = "imp"
      return this._http.post(postURLs[use], postBodies[use]).pipe(
        tap(data => console.log(`Data from Hello World: ${JSON.stringify(data)}`))
      );
    }
  }

}
