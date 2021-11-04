import { Component, OnInit } from '@angular/core';
import { impData } from '../data/implementations.data';
import { Implementation } from '../model/implementation.model';

@Component({
  selector: 'lao-implementation',
  templateUrl: './implementation.component.html',
  styleUrls: ['./implementation.component.scss']
})
export class ImplementationComponent implements OnInit {

  implmentation: Implementation = impData;

  constructor() { }

  ngOnInit(): void {
  }

}
