import { Component, OnInit } from '@angular/core';

import { Classification } from '../model/classification.model';
import { simpleClassifications } from '../data/classifications.data';
import { HttpService } from '../http.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'lao-classifications',
  templateUrl: './classifications.component.html',
  styleUrls: ['./classifications.component.scss']
})
export class ClassificationsComponent implements OnInit {

  debugMode: boolean = false;
  state: string = "default";
  formError: boolean = false;
  classificationsToDelete: {[key: string]: boolean} = Object.create({});
  classificationList: Classification[];
  root: Classification;
  newClassForm: FormGroup;
  mergeForm: FormGroup;

  resetClassificationsComponent() {
    this.state = "loading";
    this.formError = false;
    this.classificationList = [];
    this.root = new Classification('root', 'root', [], [], null);
    this.http.getClassifications().subscribe({
      next: data => {
        console.log(`Classifications: ${JSON.stringify(data)}`);
        let tree = this.buildClassificationTree(data);
        this.root = new Classification('root', 'root', [], tree, null);
        this.classificationList = data;
        this.state="default";
      }, error: err => {
        this.state="error";
        console.error(`Falling back. Classification error: ${JSON.stringify(err)}`);
        this.root = new Classification('root', 'root', [], simpleClassifications, null);
      }
    });

    this.newClassForm = this.fb.group({
      name: ['', Validators.required],
      parent: ['null', Validators.required]
    });
    this.mergeForm = this.fb.group({
      name: ['', Validators.required],
    });
  }

  addClassification() {
    this.state = "submitting";
    this.formError = false;
    if (!this.newClassForm.valid) {
      return;
    }
    let parent = null;
    if (this.newClassForm.get('parent').value !== 'null') {
      parent = this.newClassForm.get('parent').value;
    }
    this.http.addClassification(this.newClassForm.get('name').value, parent).subscribe({
      next: data => {
        this.resetClassificationsComponent();
      }, error: err => {
        this.formError = true;
        this.state = "default";
      }
    });
  }

  buildClassificationTree (classifications: Classification[]): Classification[] {
    let classTree = Object.create({});
    let topClassifications: Classification[] = [];
    for (let c of classifications) {
      classTree[c.id] = c;
      c.children = [];
    }
    for (let c of classifications) {
      if (c.parentClassificationId){
        if (c.parentClassificationId in classTree) {
          classTree[c.parentClassificationId].children.push(c)
        }
      } else {
        topClassifications.push(c);
      }
    }

    return topClassifications;
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
