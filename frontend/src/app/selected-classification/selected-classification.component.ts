import { Component, OnInit } from '@angular/core';
import { Classification } from '../model/classification.model';
import { Algorithm, Algorithm2 } from '../model/algorithm.model';
import { algoClassification, simpleClassifications } from '../data/classifications.data';
import { HttpService } from '../http.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'lao-selected-classification',
  templateUrl: './selected-classification.component.html',
  styleUrls: ['./selected-classification.component.scss']
})
export class SelectedClassificationComponent implements OnInit {

  debugMode: boolean = false;
  state: string;
  formState: string;

  classification: Classification;
  id: string;
  trueId: string;
  classifications: Classification[];
  algos: Algorithm2[];
  reclassification: "";
  selected: boolean[] = [];
  newAlgoForm = this.fb.group({
    "name": ['', Validators.required],
    "algorithmDetails": ['', Validators.required],
    // "classificationId": ['', Validators.required]
  })

  toggleCheck(i: number): boolean {
    this.selected[i] = !this.selected[i];
    return this.selected[i];
  }

  selectionExists(): boolean {
    for (let s of this.selected) {
      if (s) {
        return true;
      }
    }
    return false;
  }

  addAlgo(): void {
    if(!this.classification) {
      console.error('Cannot add a new algorithm without a confirmed parent classification');
    }
    this.state = 'submitting';
    this.http.addAlgorithm(
      this.newAlgoForm.get('name').value,
      // this.newAlgoForm.get('classificationId').value,
      this.classification.id,
      this.newAlgoForm.get('algorithmDetails').value,
    ).subscribe({
      next: data => {
        this.resetComp();
      }, error: err => {
        this.formState = "error";
        this.state = "default";
      }
    })
  }

  deleteAlgo(id: string): void {
    this.http.deleteAlgorithm(id).subscribe({
      next: data => {
        console.log(`Algorithm deleted: ${JSON.stringify(data)}`);
      }
    })
  }

  reclassifyAlgorithm(id: string): void {
    this.http.reclassifyAlgorithm(this.reclassification, id).subscribe({
      next: data => {
        console.log(data);
      }
    });
  }

  reclassify(): void {
    for (let i = 0; i < this.algos.length; i++) {
      if (this.selected[i]) {
        this.reclassifyAlgorithm(this.algos[i].id)
      }
    }
  }

  resetComp(): void {
    this.state = "loading";
    this.formState = "default";
    this.newAlgoForm = this.fb.group({
      "name": ['', Validators.required],
      "algorithmDetails": ['', Validators.required],
      // "classificationId": ['', Validators.required]
    });
    this.http.getAlgorithms().subscribe({
      next: data => {
        this.algos = data.filter(algo => algo.classificationId === this.trueId);
        // this.algos = data;
        for(let algo of data) {
          this.selected.push(false)
        }
        this.state = "default";
      }, error: err => {
        console.error(`error: ${JSON.stringify(err)}`);
        this.algos = algoClassification.algos;
        for(let algo of this.algos) {
          this.selected.push(false)
        }
        this.state = "error";
      }
    });
  }

  debug(): void {
    debugger;
  }

  constructor(
    private http: HttpService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.state = "loading";
    this.id = this.route.snapshot.paramMap.get('id');
    this.trueId = this.id;
    this.http.getClassifications().subscribe({
      next: data => {
        this.classifications = data;
        for (let clss of this.classifications) {
          if (this.id === clss.id) {
            this.id = clss.name;  // Set a name rather than an ID
            this.classification = clss;
          }
        }
      }, error: err => {
        console.error(`error: ${JSON.stringify(err)}`);
        this.classifications = simpleClassifications;
      }
    });
    this.resetComp();
  }

}
