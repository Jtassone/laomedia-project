import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AlgorithmComponent } from './algorithm/algorithm.component';
import { ClassificationsComponent } from './classifications/classifications.component';
import { Classification } from './model/classification.model';
import { SelectedClassificationComponent } from './selected-classification/selected-classification.component';


const routes: Routes = [
  { path: 'classifications', component: ClassificationsComponent },
  { path: 'classification-view/:id', component: SelectedClassificationComponent },
  { path: 'algorithm', component: AlgorithmComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
