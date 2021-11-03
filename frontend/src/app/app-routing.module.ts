import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AlgorithmComponent } from './algorithm/algorithm.component';
import { ClassificationsComponent } from './classifications/classifications.component';


const routes: Routes = [
  { path: 'classifications', component: ClassificationsComponent },
  { path: 'algorithm', component: AlgorithmComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
