import { Component, OnInit, Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
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
  @Input() mergeName: string;
  @Output("resetPage") resetPage: EventEmitter<any> = new EventEmitter();
  deleting: boolean = false;

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
    this.deleting = true;
    this.http.deleteClassification(this.classification.id).subscribe({
      next: data => {
        this.deleting = false;
        this.resetPage.emit();
      }, error: err => {
        this.resetPage.emit();
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
    this.http.mergeClassifications(this.mergeName, ids[0].id, ids[1].id).subscribe({
      next: data => {
        this.resetPage.emit();
      }, error: err => {
        this.resetPage.emit();
      }
    })
    return true
  }

  resetParent(): void {
    this.resetPage.emit();
  }

  debug(): void {
    debugger;
  }

}
