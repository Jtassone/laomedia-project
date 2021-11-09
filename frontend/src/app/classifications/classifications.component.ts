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

  root: Classification;
  newClassForm = this.fb.group({
    name: ['', Validators.required],
  })

  resetClassifications() {
    this.root = new Classification('root', 'root', [], []);
    this.http.getClassifications().subscribe({
      next: data => {
        console.log(`Classifications: ${JSON.stringify(data)}`);
        this.root = new Classification('root', 'root', [], data);
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
    if (!this.newClassForm.valid) {
      return;
    }
    this.http.addClassification(this.newClassForm.get('name').value).subscribe({
      next: data => {
        this.resetClassifications();
      }
    })
  }

  constructor(
    private http: HttpService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.root = new Classification('root', 'root', [], []);
    this.http.getClassifications().subscribe({
      next: data => {
        console.log(`Classifications: ${JSON.stringify(data)}`);
        this.root = new Classification('root', 'root', [], data);
      }, error: err => {
        console.error(`Falling back. Classification error: ${JSON.stringify(err)}`);
        this.root = new Classification('root', 'root', [], simpleClassifications);
      }
    });

    // this.http.postTest().subscribe({
    //   next: data => {
    //     console.log(`The data again: ${JSON.stringify(data)}`)
    //   }, error: err => {
    //     console.error(`The hello world endpoint isn't working: ${JSON.stringify(err)}\n\nMessage: ${err.message}`);
    //   }
    // });

  }

}
