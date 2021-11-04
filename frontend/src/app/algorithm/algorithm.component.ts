import { Component, OnInit } from '@angular/core';
import { algoData } from '../data/algorithms.data';

@Component({
  selector: 'lao-algorithm',
  templateUrl: './algorithm.component.html',
  styleUrls: ['./algorithm.component.scss']
})
export class AlgorithmComponent implements OnInit {

  algorithm = algoData;

  constructor() { }

  ngOnInit(): void {
  }

}
