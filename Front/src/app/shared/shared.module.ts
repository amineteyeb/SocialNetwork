import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './nav/nav.component';
import { SidebarComponent } from './sidebar/sidebar.component';



@NgModule({
  declarations: [],
  imports: [
    
  NavComponent,
  SidebarComponent,
    CommonModule
  ],
  exports: [
    
    NavComponent,
    SidebarComponent,
     
    ]
})
export class SharedModule { 

}
