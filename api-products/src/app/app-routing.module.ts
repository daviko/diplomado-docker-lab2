import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListProductComponent } from './list-product/list-product.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { ErrorComponent } from './error/error.component';


const routes: Routes = [
  {path: '', component: ListProductComponent},
  {path: 'list-product', component: ListProductComponent},
  {path: 'edit-product/:id', component: EditProductComponent},
  {path: '**', component: ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
