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

  impState: 'loading' | 'ready' | 'submitting' | 'error';
  instState: 'loading' | 'ready' | 'submitting' | 'error';
  formState: 'loading' | 'ready' | 'submitting' | 'error';
  implementations: Implementation[] = [];
  implementation: Implementation;
  instances: Instance[];
  id: string;
  self: Instance;
  trueId: string;
  state: string;
  impForm: FormGroup;
  instFile: any;

  private setting = {
    element: {
      dynamicDownload: null as HTMLElement
    }
  }

  fileChanged = e => this.instFile = e.target.files[0];

  addInstance(): void {
    this.uploadDocument();
  }

  uploadDocument() {
    this.formState = 'submitting';
    let fileReader = new FileReader();
    fileReader.onload = e => {
      console.log(fileReader.result);
      const b64string = btoa(fileReader.result as string)
      console.log(b64string);
      let newInstance: Instance = {
        id: null,
        name: this.impForm.get('name').value,
        implementationId: this.trueId,
        algorithmId: this.self.algorithmId,
        instanceFileString: b64string
      } as Instance
      this.http.addInstance(newInstance).subscribe({
        next: data => {
          this.formState = 'ready';
          this.resetComp();
        }
      })
    }
    fileReader.readAsBinaryString(this.instFile);
  }

  inNewTab(): void {
    const blob = new Blob([this.implementation.implementationDetails], {type: 'text/text'});
    const url = window.URL.createObjectURL(blob);
    window.open(url);
  }

  downloadFile(): void {
    this.dyanmicDownloadByHtmlTag({fileName: `implementation-${this.implementation.name}.txt`, text: this.implementation.implementationDetails})
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

  constructor(
    private http: HttpService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) { }

  resetComp(): void {
    this.instState = 'loading';
    this.http.getInstances().subscribe({
      next: data => {
        this.instances = data;
        this.instState = 'ready';
      }, error: err => {
        this.instState = 'error';
      }
    })
    this.impForm = this.fb.group({
      "name": ['', Validators.required],
      "upload": ['', Validators.required],
    })
  }

  ngOnInit(): void {
    this.impState = 'loading';
    this.formState = 'ready';
    this.id = this.route.snapshot.paramMap.get('id');
    this.trueId = this.id;
    this.http.getImplementations().subscribe({
      next: data => {
        // this.implementations = data;
        this.implementations = data.filter(imp => imp.id === this.trueId);
        for (let imp of data) {
          if (imp.id === this.id) {
            this.id = imp.name;
            this.trueId = imp.id;
            this.implementation = imp;
            this.self = imp;
          }
        }
        this.state = "default";
        this.impState = 'ready';
      },
      error: err => {
        this.state = "error";
        console.log(`Problem loading implementations: ${JSON.stringify(err)}\n\n${err.message}`);
        this.implementation = impData;
        this.implementations = impList;
        this.instState = 'error';
      }
    });
    this.resetComp();
  }

}
