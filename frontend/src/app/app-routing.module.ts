import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AlgorithmComponent } from './algorithm/algorithm.component';
import { ClassificationsComponent } from './classifications/classifications.component';
import { Classification } from './model/classification.model';
import { SelectedClassificationComponent } from './selected-classification/selected-classification.component';


const routes: Routes = [
  { path: 'classifications-view', component: ClassificationsComponent },
  { path: 'classification-view/:id', component: SelectedClassificationComponent },
  { path: 'algorithm-view/:id', component: AlgorithmComponent },
  { path: '', redirectTo: '/classifications-view', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
