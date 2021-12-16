import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { instData } from '../data/instances.data';
import { HttpService } from '../http.service';
import Benchmark from '../model/benchmark.model';
import { Instance } from '../model/instance.model';

@Component({
  selector: 'lao-instance',
  templateUrl: './instance.component.html',
  styleUrls: ['./instance.component.scss']
})
export class InstanceComponent implements OnInit {

  instance: Instance = instData;
  benchForm: FormGroup;
  formState: 'loading' | 'ready' | 'submitting' | 'error';
  instState: 'loading' | 'ready' | 'submitting' | 'error';
  benchState: 'loading' | 'ready' | 'submitting' | 'error';
  benchmarks: Benchmark[] = [];
  id: string;
  trueId: string;
  instBody: string;

  // columnsToDisplay = ['id', 'machineConfig'];
  expandedElement: Benchmark | null;
  // columnsToDisplay = ['delete', 'date','core','cpu','l1','l2','l3','numberThreads','ram']
  columnsToDisplay = ['date','core','cpu','l1','l2','l3','numberThreads','ram']

  private setting = {
    element: {
      dynamicDownload: null as HTMLElement
    }
  }

  addBenchmark() {
    let newBench: Benchmark = {
      id: null,
      date: this.benchForm.get('date').value.toISOString().substring(0,10),
      instanceId: this.trueId,
      implementationId: this.instance.implementationId,
      core: this.benchForm.get('core').value,
      cpu: this.benchForm.get('cpu').value,
      l1: this.benchForm.get('l1').value,
      l2: this.benchForm.get('l2').value,
      l3: this.benchForm.get('l3').value,
      numberThreads: this.benchForm.get('numberThreads').value,
      ram: this.benchForm.get('ram').value,
    } as Benchmark
    this.http.addBenchmark(newBench).subscribe({
      next: data => {
        console.log(data);
        this.resetComponent();
      }, error: err => {
        this.formState = 'error';
      }
    })
  }

  deleteBenchmark(): void {

  }

  inNewTab(): void {
    const blob = new Blob([this.instBody], {type: 'text/text'});
    const url = window.URL.createObjectURL(blob);
    window.open(url);
  }

  downloadFile(): void {
    this.dyanmicDownloadByHtmlTag({fileName: `instance-${this.instance.name}.txt`, text: this.instBody})
  }

  private dyanmicDownloadByHtmlTag(arg: {
    fileName: string,
    text: string
    }) {
    if (!this.setting.element.dynamicDownload) {
      this.setting.element.dynamicDownload = document.createElement('a');
    }
    const element = this.setting.element.dynamicDownload;
    const fileType = arg.fileName.indexOf('.json') > -1 ? 'text/json' : 'text/plain';
    element.setAttribute('href', `data:${fileType};charset=utf-8,${encodeURIComponent(arg.text)}`);
    element.setAttribute('download', arg.fileName);

    var event = new MouseEvent("click");
    element.dispatchEvent(event);
  }

  resetComponent(): void {
    this.formState = 'ready';
    this.benchState = 'loading';
    this.instState = 'loading';
    this.http.getInstances().subscribe({
      next: data => {
        for (let inst of data) {
          if (inst.id = this.id) {
            this.id = inst.name;
            this.instance = inst;
            this.instBody = atob(inst.instanceFileString);
            this.instState = 'ready';
          }
          this.http.getBenchmarks().subscribe({
            next: data => {
              console.log(data);
              this.benchState = 'ready';
              this.benchmarks = data.filter(inst => inst.instanceId === this.trueId);
            }
          });
        }
        if (!this.instance){
          this.instState = 'error';
        }
      }, error: err => {
        this.instState = 'error';
      }
    })
    this.benchForm = this.fb.group({
      "date": ['', Validators.required],
      "core": ['', Validators.required],
      "cpu": ['', Validators.required],
      "l1": ['', Validators.required],
      "l2": ['', Validators.required],
      "l3": ['', Validators.required],
      "numberThreads": ['', Validators.required],
      "ram": ['', Validators.required],
    })
  }

  constructor(
    private http: HttpService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.trueId = this.id;
    this.instBody = '-- Loading Instance --'
    this.resetComponent();
  }

}
