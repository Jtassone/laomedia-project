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

  debug(): void {
    debugger;
  }

}
