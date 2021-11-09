import { Component, OnInit } from '@angular/core';
import { Classification } from '../model/classification.model';
import { algoClassification, simpleClassifications } from '../data/classifications.data';
import { HttpService } from '../http.service';
import { ActivatedRoute } from '@angular/router';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'lao-selected-classification',
  templateUrl: './selected-classification.component.html',
  styleUrls: ['./selected-classification.component.scss']
})
export class SelectedClassificationComponent implements OnInit {

  classification: Classification;
  id: string
  classifications: Classification[];
  algos: Algorithm[];
  reclassification: "";
  selected: boolean[] = [];

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

  constructor(
    private http: HttpService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')
    this.http.getAlgorithms().subscribe({
      next: data => {
        this.algos = data;
        for(let algo of data) {
          this.selected.push(false)
        }
      }, error: err => {
        console.error(`error: ${JSON.stringify(err)}`);
        this.algos = algoClassification.algos;
        for(let algo of this.algos) {
          this.selected.push(false)
        }
      }
    });
    this.http.getClassifications().subscribe({
      next: data => {
        this.classifications = data;
      }, error: err => {
        console.error(`error: ${JSON.stringify(err)}`);
        this.classifications = simpleClassifications;
      }
    })
  }

}
