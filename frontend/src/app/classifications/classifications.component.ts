import { Component, OnInit } from '@angular/core';

import { Classification } from '../model/classification.model';
import { simpleClassifications } from '../data/classifications.data';

@Component({
  selector: 'lao-classifications',
  templateUrl: './classifications.component.html',
  styleUrls: ['./classifications.component.scss']
})
export class ClassificationsComponent implements OnInit {

  root: Classification;

  constructor() { }

  ngOnInit(): void {
    this.root = new Classification('root', 'root', [], simpleClassifications)
  }

}
