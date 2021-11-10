import { Component, OnInit } from '@angular/core';
import { algoData } from '../data/algorithms.data';
import { HttpService } from '../http.service';
import { ActivatedRoute } from '@angular/router';
import Implementation from '../model/implementation.model';
import Algorithm from '../model/algorithm.model';
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
  algorithm: Algorithm = algoData;
  algorithms: Algorithm[] = [];
  implementations: Implementation[];
  newImpForm = this.fb.group({
    "name": ['', Validators.required],
    "algorithmId": ['', Validators.required],
    "implementationDetails": ['', Validators.required],
  });

  getValue(): string {
    return JSON.stringify(this.newImpForm.value)
  }

  addImp(): void {
    this.state = "submitting";
    this.http.addImplementation(
      this.newImpForm.get('name').value,
      this.newImpForm.get('algorithmId').value,
      this.newImpForm.get('implementationDetails').value
    ).subscribe({
      next: data => {
        this.state = "default";
        this.resetComp();
      }, error: err => {
        this.state = "error";
      }
    })
  }

  resetComp(): void {
    this.state = "loading";
    this.http.getImplementations().subscribe({
      next: data => {
        this.implementations = data;
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
      "algorithmId": ['', Validators.required],
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
    this.http.getAlgorithms().subscribe({
      next: (data: Algorithm[]) => {
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
