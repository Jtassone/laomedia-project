import { Component, OnInit } from '@angular/core';

import { Classification } from '../model/classification.model';
import { simpleClassifications } from '../data/classifications.data';
import { HttpService } from '../http.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'lao-classifications',
  templateUrl: './classifications.component.html',
  styleUrls: ['./classifications.component.scss']
})
export class ClassificationsComponent implements OnInit {

  debugMode: boolean = true;
  state: string = "default";

  root: Classification;
  newClassForm = this.fb.group({
    name: ['', Validators.required],
  })

  resetClassificationsComponent() {
    this.state = "loading";
    this.root = new Classification('root', 'root', [], []);
    this.http.getClassifications().subscribe({
      next: data => {
        console.log(`Classifications: ${JSON.stringify(data)}`);
        this.root = new Classification('root', 'root', [], data);
        this.state="default"
      }, error: err => {
        console.error(`Falling back. Classification error: ${JSON.stringify(err)}`);
        this.root = new Classification('root', 'root', [], simpleClassifications);
      }
    });

    this.newClassForm = this.fb.group({
      name: ['', Validators.required],
    })
  }

  addClassification() {
    this.state = "submitting"
    if (!this.newClassForm.valid) {
      return;
    }
    this.http.addClassification(this.newClassForm.get('name').value).subscribe({
      next: data => {
        this.resetClassificationsComponent();
      }
    })
  }

  debug() {
    debugger;
  }

  constructor(
    private http: HttpService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.resetClassificationsComponent();
  }

}
