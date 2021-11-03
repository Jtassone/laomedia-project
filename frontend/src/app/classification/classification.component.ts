import { Component, OnInit, Input } from '@angular/core';
import { Classification } from '../model/classification.model';

@Component({
  selector: 'lao-classification',
  templateUrl: './classification.component.html',
  styleUrls: ['./classification.component.scss']
})
export class ClassificationComponent implements OnInit {

  @Input() classification: Classification;

  constructor() { }

  ngOnInit(): void {
  }

}
