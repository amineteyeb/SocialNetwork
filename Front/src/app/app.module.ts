import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MatInputModule } from "@angular/material/input";
import { MatCardModule } from "@angular/material/card";
import { MatIconModule } from '@angular/material/icon'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule,HTTP_INTERCEPTORS  } from '@angular/common/http';
//import { JwtInterceptor } from './service/auth/JwtInterceptor';


import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { RegisterComponent } from './register/register.component';
import { AlertComponent } from './alert/alert.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { HomeComponent } from './home/home.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import { AuthGuardService } from './service/auth/authGuard';
import { CommonModule } from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {MatDividerModule} from '@angular/material/divider';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';
import { MyShopComponent } from './my-shop/my-shop.component';
import { TermsandservicesComponent } from './termsandservices/termsandservices.component';
import { ProductAddComponent } from './product-add/product-add.component';
import { MatTableModule } from '@angular/material/table';
import {MatGridListModule} from '@angular/material/grid-list';
import { AuthInterceptor } from './service/auth/auth.interceptor';
import { JwtInterceptor } from './service/auth/JwtInterceptor';
import { AllProductsComponent } from './all-products/all-products.component';
import { DetailProductComponent } from './detail-product/detail-product.component';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { CartComponent } from './cart/cart.component';
import { BuyProductComponent } from './buy-product/buy-product.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { OrderConfirmationComponent } from './order-confirmation/order-confirmation.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { NavComponent } from './shared/nav/nav.component';
import { SharedModule } from './shared/shared.module';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    AlertComponent,
    NavBarComponent,
    HomeComponent,
    WelcomePageComponent,
    MyShopComponent,
    TermsandservicesComponent,
    ProductAddComponent,
    AllProductsComponent,
    DetailProductComponent,
    CartComponent,
    BuyProductComponent,
    MyOrdersComponent,
    OrderConfirmationComponent,
    OrderDetailsComponent,
 
    
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonToggleModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSelectModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatGridListModule ,
    MatToolbarModule,
    MatTableModule,
    FormsModule,
    SharedModule,
    ReactiveFormsModule,
    HttpClientModule ,
  CommonModule,
  MatButtonModule,
  MatDividerModule,
  ],
  providers: [AuthGuardService,
   /*{
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },*/
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
