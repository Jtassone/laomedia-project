import { Component, OnInit } from '@angular/core';
import { algoData } from '../data/algorithms.data';
import { HttpService } from '../http.service';
import { ActivatedRoute } from '@angular/router';
import Implementation from '../model/implementation.model';
import {Algorithm, Algorithm2} from '../model/algorithm.model';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'lao-algorithm',
  templateUrl: './algorithm.component.html',
  styleUrls: ['./algorithm.component.scss']
})
export class AlgorithmComponent implements OnInit {

  state: string = "loading";
  algoState: string = "loading";
  id: string = "No Algorithm Selected";
  trueId: string;
  algorithm: Algorithm2 = algoData;
  algorithms: Algorithm2[] = [];
  implementations: Implementation[];
  impFile: any;
  newImpForm = this.fb.group({
    "name": ['', Validators.required],
    // "algorithmId": ['', Validators.required],
    "implementationDetails": ['', Validators.required],
  });

  getValue(): string {
    return JSON.stringify(this.newImpForm.value)
  }

  debug(): void {
    debugger;
  }

  fileChanged = e => this.impFile = e.target.files[0];

  uploadDocument() {
    let fileReader = new FileReader();
    fileReader.onload = e => {
      console.log(fileReader.result);
      const b64string = btoa(fileReader.result as string)
      console.log(b64string);
      this.http.addImplementation(
        this.newImpForm.get('name').value,
        // this.newImpForm.get('algorithmId').value,
        this.algorithm.id,
        b64string
      ).subscribe({
        next: data => {
          this.state = "default";
          this.resetComp();
        }, error: err => {
          this.state = "error";
        }
      })
    }
    fileReader.readAsBinaryString(this.impFile);
  }

  addImp(): void {
    this.state = "submitting";
    this.uploadDocument();
    // this.http.addImplementation(
    //   this.newImpForm.get('name').value,
    //   // this.newImpForm.get('algorithmId').value,
    //   this.algorithm.id,
    //   this.newImpForm.get('implementationDetails').value
    // ).subscribe({
    //   next: data => {
    //     this.state = "default";
    //     this.resetComp();
    //   }, error: err => {
    //     this.state = "error";
    //   }
    // })
  }

  deleteImplementation(id: string): void {
    this.http.deleteImplementation(id).subscribe({
      next: data => {
        console.log(`Deleted implementation ${JSON.stringify(data)}`);
      }
    })
  }

  resetComp(): void {
    this.state = "loading";
    this.http.getImplementations().subscribe({
      next: data => {
        // this.implementations = data;
        this.implementations = data.filter(imp => imp.algorithmId === this.trueId);
        this.state = "default";
      },
      error: err => {
        this.state = "error";
        console.log(`Problem loading implementations: ${JSON.stringify(err)}\n\n${err.message}`);
        this.algorithm = algoData;
        this.implementations = algoData.imps;
      }
    });
    this.newImpForm = this.fb.group({
      "name": ['', Validators.required],
      "upload": ['', Validators.required],
      "implementationDetails": ['', Validators.required],
    });
  }

  constructor(
    private http: HttpService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.trueId = this.id;
    this.http.getAlgorithms().subscribe({
      next: (data: Algorithm2[]) => {
        for (let algo of data) {
          this.algorithms = data;
          if (algo.id === this.id) {
            this.id = algo.name;
            this.algorithm = algo;
            this.algoState = "default";
          }
        }
      }, error: err => {
        this.algoState = "error";
      }
    });
    this.resetComp();
  }

}
