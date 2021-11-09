import { Component, OnInit } from '@angular/core';
import { instData } from '../data/instances.data';
import Benchmark from '../model/benchmark.model';
import { Instance } from '../model/instance.model';

@Component({
  selector: 'lao-instance',
  templateUrl: './instance.component.html',
  styleUrls: ['./instance.component.scss']
})
export class InstanceComponent implements OnInit {

  instance: Instance = instData;

  // columnsToDisplay = ['id', 'machineConfig'];
  expandedElement: Benchmark | null;
  columnsToDisplay = ['id','core','CPU','L1','L2','L3','RAM']

  constructor() { }

  ngOnInit(): void {
  }

}
