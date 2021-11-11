import { Component, OnInit } from '@angular/core';
import { impData, impList } from '../data/implementations.data';
import { HttpService } from '../http.service';
import { FormBuilder, Validators } from '@angular/forms';
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
  trueId: string;
  state: string;

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
  }

}
