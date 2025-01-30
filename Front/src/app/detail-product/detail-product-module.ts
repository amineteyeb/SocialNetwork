// detail-product.module.ts

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatGridListModule } from '@angular/material/grid-list';
import { NavBarComponent } from '../nav-bar/nav-bar.component'; // Adjust the path accordingly
import { DetailProductComponent } from './detail-product.component';

NgModule({
  declarations: [DetailProductComponent, NavBarComponent],
  imports: [CommonModule, MatGridListModule],
})
export class DetailProductModule {}
