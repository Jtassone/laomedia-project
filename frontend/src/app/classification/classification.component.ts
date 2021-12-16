import { Component, OnInit, Input } from '@angular/core';
import { HttpService } from '../http.service';
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

  constructor(private http: HttpService) { }

  ngOnInit(): void {
    this.checked = [];
    if (this.classification.children) {
      for (let i = 0; i < this.classification.children.length; i++) {
        this.checked.push(false);
      }
    }
  }

  deleteClassification(): void {
    this.http.deleteClassification(this.classification.id).subscribe({
      next: data => {
        console.log(`yay: ${JSON.stringify(data)}`)
      }
    });
  }

  mergeChildren(): boolean {
    let ids: Classification[] = [];
    for (let i = 0; i < this.checked.length; i++) {
      if(this.checked[i]) {
        ids.push(this.classification.children[i])
      }
    }
    if (ids.length !== 2) {
      return false;
    }
    this.http.mergeClassifications(ids[0].name, ids[0].id, ids[1].id).subscribe({
      next: data => {
        console.log(`data: ${data}`);
      }
    })
    return true
  }

  debug(): void {
    debugger;
  }

}
