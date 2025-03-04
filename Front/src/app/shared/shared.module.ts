import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './nav/nav.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserModule } from '@angular/platform-browser';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { AppRoutingModule } from '../app-routing.module';

@NgModule({
  declarations: [  NavComponent,
    SidebarComponent],
  imports: [
    BrowserModule,
    MatSnackBarModule,
    AppRoutingModule
   
  ],
  exports: [
    MatSnackBarModule,
    NavComponent,
    SidebarComponent,
     
    ]
})
export class SharedModule { 

}
