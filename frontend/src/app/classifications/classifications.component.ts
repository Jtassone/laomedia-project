import { Component, OnInit } from '@angular/core';

import { Classification } from '../model/classification.model';
import { simpleClassifications } from '../data/classifications.data';
import { HttpService } from '../http.service';

@Component({
  selector: 'lao-classifications',
  templateUrl: './classifications.component.html',
  styleUrls: ['./classifications.component.scss']
})
export class ClassificationsComponent implements OnInit {

  root: Classification;

  constructor(private http: HttpService) { }

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
  }

}
