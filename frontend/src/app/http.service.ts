import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, tap, delay } from 'rxjs/operators'
import { Observable } from 'rxjs';

import { Classification } from './model/classification.model';
import { Algorithm2 } from './model/algorithm.model';
import Implementation from './model/implementation.model';
import { UserEvent } from './model/userEvent.model';
import { IUser } from './model/user.model';
import { Instance } from './model/instance.model';
import { Benchmark } from './model/benchmark.model';
import { AuthService } from './auth.service';



@Injectable({
  providedIn: 'root'
})
export class HttpService {

  baseURL = 'https://8jyixjqsxb.execute-api.us-east-1.amazonaws.com/Prod/';
  delay = 0;

  urls = {
    get: {
      algos: "https://jsp4qhazd6.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-getAlgorithms",
      class: "https://idxllinx9l.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-getClassification",
      imp: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-getImplementation",
      events: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/users/",
      users: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/users",
      inst: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/instances",
      bench: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/benchmarks",
    }, post: {
      algos: "https://lnpmfr4e8l.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-postAlgorithm",
      class: "https://6jj1ay30h6.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-postClassification",
      classMerge: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/classifications/merge",
      imp: "https://h1l85l4gp8.execute-api.us-east-1.amazonaws.com/default/awscodestar-laomedia-projec-lambda-postImplementation",
      inst: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/instances",
      bench: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/benchmarks",
      reclassify: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/algorithms/reclassify",
    }, delete: {
      class: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/classifications/",
      algos: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/algorithms/",
      imp: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/implementations/",
      user: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/users/",
      bench: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/benchmarks/",
      inst: "https://3jot1u1xb5.execute-api.us-east-1.amazonaws.com/default/instances/",
    }
  }

  constructor(
    private _http: HttpClient,
    private auth: AuthService,
  ) { }

  getClassifications(): Observable<Classification[]> {
    const url = `${this.urls.get['class']}?userName=${this.auth.getUsername()}`;
    return this._http.get<Classification[]>(url).pipe(
      tap(data => console.log(`Received classifications`))
    )
  }

  getClassification(id: string): Observable<Classification> {
    const url = `${this.urls.get['class']}?userName=${this.auth.getUsername()}`;
    return this._http.get<Classification>(url).pipe(
      tap(data => console.log(`Received classification`))
    )
  }

  addClassification(name: string, parent: string): Observable<Classification> {
    const url = `${this.urls.post['class']}?userName=${this.auth.getUsername()}`;
    const body = JSON.stringify({name: name, parentClassificationId: parent, userName: this.auth.getUsername()});
    return this._http.post<Classification>(url, body).pipe(
      tap(data => console.log(`Classification added`))
    );
  }

  deleteClassification(id: string): Observable<any> {
    const url = `${this.urls.delete['class']}${id}?userName=${this.auth.getUsername()}`;
    return this._http.delete<any>(url).pipe(
      tap(data => console.log(`Deleted Classification`))
    )
  }

  mergeClassifications(name: string, id1: string, id2: string) {
    const body = {
      newClassificationName: name,
      classification1Id: id1,
      classification2Id: id2,
    }
    const url = `${this.urls.post.classMerge}?userName=${this.auth.getUsername()}`;
    return this._http.post<any>(url, body).pipe(
      tap(data => console.log(`Merged Algorithms`))
    );
  }

  getAlgorithms(): Observable<Algorithm2[]> {
    const url = `${this.urls.get['algos']}?userName=${this.auth.getUsername()}`;
    return this._http.get<Algorithm2[]>(url).pipe(
      tap(data => console.log(`Received Algorithms`))
    )
  }

  reclassifyAlgorithm(classificationId: string, algorithmId: string): Observable<Algorithm2[]> {
    const url = `${this.urls.post.reclassify}?userName=${this.auth.getUsername()}`;
    const body = {classificationId, algorithmId}
    return this._http.post<Algorithm2[]>(url, body).pipe(
      tap(data => console.log(`Received Algorithms`))
    )
  }

  addAlgorithm(name: string, classificationId: string, algorithmDetails: string): Observable<any> {
    const url = `${this.urls.post['algos']}?userName=${this.auth.getUsername()}`;
    const body = {
      name,
      classificationId,
      algorithmDetails
    }
    return this._http.post<any>(url, body).pipe(
      tap(data => console.log(`Added Algorithm`))
    );
  }

  deleteAlgorithm (id: string): Observable<any> {
    const url = `${this.urls.delete['algos']}${id}?userName=${this.auth.getUsername()}`;
    return this._http.delete<any>(url).pipe(
      tap(data => console.log(`Deleted Algorithm`))
    )
  }

  getImplementations(): Observable<Implementation[]> {
    const url = `${this.urls.get['imp']}?userName=${this.auth.getUsername()}`;
    return this._http.get<Implementation[]>(url).pipe(
      tap(data => console.log(`Received Implementations`)),
      map(data => {
        data.map(imp => {
          imp.implementationDetails = atob(imp.implementationDetails)
        })
        return data;
      }),
      delay(this.delay)
    );
  }

  addImplementation(name: string, algorithmId: string, implementationDetails: string): Observable<any> {
    const url = `${this.urls.post['imp']}?userName=${this.auth.getUsername()}`;
    const body = {
      name,
      algorithmId,
      implementationDetails
    }
    return this._http.post<any>(url, body).pipe(
      tap(data => console.log(`Added implementation`))
    );
  }

  deleteImplementation (id: string): Observable<any> {
    const url = `${this.urls.delete['imp']}${id}?userName=${this.auth.getUsername()}`;
    return this._http.delete<any>(url).pipe(
      tap(data => console.log(`Deleted Implementation`))
    )
  }

  getInstances(): Observable<Instance[]> {
    const url = `${this.urls.get.inst}?userName=${this.auth.getUsername()}`;
    return this._http.get<Instance[]>(url).pipe(
      tap(data => console.log(`Received Instances`)),
      delay(this.delay)
    );
  }

  addInstance(inst: Instance): Observable<any> {
    const url = `${this.urls.post.inst}?userName=${this.auth.getUsername()}`;
    const body = {
      name: inst.name,
      algorithmId: inst.algorithmId,
      instanceFileString: inst.instanceFileString,
      implementationId: inst.implementationId,
    }
    return this._http.post<any>(url, body).pipe(
      tap(data => console.log(`Instance added`))
    );
  }

  deleteInstance (id: string): Observable<any> {
    const url = `${this.urls.delete.inst}${id}?userName=${this.auth.getUsername()}`;
    return this._http.delete<any>(url).pipe(
      tap(data => console.log(`Deleted Instance`))
    )
  }

  getBenchmarks(): Observable<Benchmark[]> {
    const url = `${this.urls.get.bench}?userName=${this.auth.getUsername()}`;
    return this._http.get<Benchmark[]>(url).pipe(
      tap(data => console.log(`Benchmarks received`))
    );
  }

  addBenchmark(bench: Benchmark): Observable<any> {
    const url = `${this.urls.post.bench}?userName=${this.auth.getUsername()}`;
    const body = bench;
    return this._http.post<any>(url, body).pipe(
      tap(data => console.log(`Added benchmark`))
    );
  }

  deleteBenchmark (id: string): Observable<any> {
    const url = `${this.urls.delete.bench}${id}?userName=${this.auth.getUsername()}`;
    return this._http.delete<any>(url).pipe(
      tap(data => console.log(`Deleted benchmark`))
    )
  }

  getUsers(): Observable<IUser[]> {
    const url = `${this.urls.get['users']}?userName=${this.auth.getUsername()}`;
    return this._http.get<IUser[]>(url);
  }

  deleteUser(user: string): Observable<any> {
    const url = `${this.urls.delete['user']}${user}?userName=${this.auth.getUsername()}`;
    return this._http.delete<any>(url);
  }

  getEvents(user: string): Observable<UserEvent[]> {
    const url = `${this.urls.get['events']}${user}?userName=${this.auth.getUsername()}`;
    return this._http.get<UserEvent[]>(url);
  }

  helloWorld(): any {
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
