import { Component, OnInit } from '@angular/core';
import { impData, impList } from '../data/implementations.data';
import { HttpService } from '../http.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Implementation } from '../model/implementation.model';
import { Instance } from '../model/instance.model';
import { instList } from '../data/instances.data';

@Component({
  selector: 'lao-implementation',
  templateUrl: './implementation.component.html',
  styleUrls: ['./implementation.component.scss']
})
export class ImplementationComponent implements OnInit {

  implementations: Implementation[] = [];
  implementation: Implementation;
  instances: Instance[] = instList;
  id: string;
  self: Instance;
  trueId: string;
  state: string;
  impForm: FormGroup;
  instFile: any;

  fileChanged = e => this.instFile = e.target.files[0];

  addInstance(): void {
    this.uploadDocument();
  }

  uploadDocument() {
    let fileReader = new FileReader();
    fileReader.onload = e => {
      console.log(fileReader.result);
      const b64string = btoa(fileReader.result as string)
      console.log(b64string);
      let newInstance: Instance = {
        id: null,
        name: this.impForm.get('name').value,
        algorithmId: this.self.algorithmId,
        instanceFileString: b64string
      } as Instance
      this.http.addInstance(newInstance).subscribe({
        next: data => {
          console.log('hi');
        }
      })
    }
    fileReader.readAsBinaryString(this.instFile);
  }


  constructor(
    private http: HttpService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.trueId = this.id;
    this.http.getImplementations().subscribe({
      next: data => {
        // this.implementations = data;
        this.implementations = data.filter(imp => imp.id === this.trueId);
        for (let imp of data) {
          if (imp.id === this.id) {
            this.id = imp.id;
            this.implementation = imp;
            this.self = imp;
          }
        }
        this.state = "default";
      },
      error: err => {
        this.state = "error";
        console.log(`Problem loading implementations: ${JSON.stringify(err)}\n\n${err.message}`);
        this.implementation = impData;
        this.implementations = impList;
      }
    });
    this.http.getInstances().subscribe({
      next: data => {
        this.instances = data;
      }
    })
    this.impForm = this.fb.group({
      "name": ['', Validators.required],
      "upload": ['', Validators.required],
    })
  }

}
