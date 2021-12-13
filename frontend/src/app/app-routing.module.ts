import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AlgorithmComponent } from './algorithm/algorithm.component';
import { ClassificationsComponent } from './classifications/classifications.component';
import { ImplementationComponent } from './implementation/implementation.component';
import { InstanceComponent } from './instance/instance.component';
import { LoginComponent } from './login/login.component';
import { Classification } from './model/classification.model';
import { SelectedClassificationComponent } from './selected-classification/selected-classification.component';
import { AuthGuard } from './auth.guard';
import { AdminComponent } from './admin/admin.component';


const routes: Routes = [
  { path: 'classifications-view', component: ClassificationsComponent, },
  { path: 'classification-view/:id', component: SelectedClassificationComponent, },
  { path: 'algorithm-view/:id', component: AlgorithmComponent },
  { path: 'implementation-view/:id', component: ImplementationComponent },
  { path: 'instance-view/:id', component: InstanceComponent },
  { path: 'login', component: LoginComponent},
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard]},
  { path: '', redirectTo: '/classifications-view', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
