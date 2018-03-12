// Modules
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// Components
import { TermComponent } from './pages/term/term.component';
import { DiseaseComponent } from './pages/disease/disease.component';
import { GeneComponent } from './pages/gene/gene.component';

const browserRoutes: Routes = [
    { path:'term/:id', component: TermComponent },
    { path:'disease/:id', component: DiseaseComponent},
    { path:'gene/:id', component: GeneComponent }
];
export const browserRouting = RouterModule.forChild(browserRoutes);
@NgModule({
  imports: [
    RouterModule,
    browserRouting
  ],
 exports: [ RouterModule ]
})
export class BrowserRoutingModule {}
