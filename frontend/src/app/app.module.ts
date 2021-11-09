import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AmplifyUIAngularModule } from '@aws-amplify/ui-angular';
import Amplify from 'aws-amplify';
import {awsconfig} from 'backend-aws-exports/dev/aws-exports';

/* Configure Amplify resources */
Amplify.configure(awsconfig);

import { ReactiveFormsModule } from '@angular/forms';

import { AlgorithmComponent } from './algorithm/algorithm.component';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ClassificationsComponent } from './classifications/classifications.component';
import { HttpClientModule } from '@angular/common/http';
import { ImplementationComponent } from './implementation/implementation.component';
import { InstanceComponent } from './instance/instance.component';


import { ClassificationComponent } from './classification/classification.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { SelectedClassificationComponent } from './selected-classification/selected-classification.component';
import { LoginComponent } from './login/login.component';

@NgModule({

  declarations: [
    AlgorithmComponent,
    AppComponent,
    ClassificationComponent,
    ClassificationsComponent,
    ImplementationComponent,
    InstanceComponent,
    SelectedClassificationComponent,
    LoginComponent,
  ],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    HttpClientModule,
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatSelectModule,
    MatTableModule,
    MatToolbarModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
