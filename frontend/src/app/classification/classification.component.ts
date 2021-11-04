import { Component, OnInit, Input } from '@angular/core';
import { Classification } from '../model/classification.model';

@Component({
  selector: 'lao-classification',
  templateUrl: './classification.component.html',
  styleUrls: ['./classification.component.scss']
})
export class ClassificationComponent implements OnInit {

  @Input() classification: Classification;
  @Input() isRoot = false;
  checked: boolean[];

  mergable(): boolean {
    let count = 0;
    for (let item of this.checked) {
      if (item) {
        count++;
      }
    }
    return count === 2;
  }

  toggleCheck(i: number): boolean {
    this.checked[i] = !this.checked[i];
    return this.checked[i];
  }

  constructor() { }

  ngOnInit(): void {
    this.checked = [];
    for (let i = 0; i < this.classification.children.length; i++) {
      this.checked.push(false);
    }
  }

  debug(): void {
    debugger;
  }

}
