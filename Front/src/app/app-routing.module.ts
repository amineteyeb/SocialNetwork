import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { AuthGuardService } from './service/auth/authGuard';
import { ProductAddComponent } from './product-add/product-add.component';
import { MyShopComponent } from './my-shop/my-shop.component';
import { AllProductsComponent } from './all-products/all-products.component';
import { DetailProductComponent } from './detail-product/detail-product.component';
import { ProductResolveService } from './service/product/ProductResolverService';
import { CartComponent } from './cart/cart.component';
import { BuyProductComponent } from './buy-product/buy-product.component';
import { OrderConfirmationComponent } from './order-confirmation/order-confirmation.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { ProductResolverService } from './service/order/ProductResolverService';
import { SignPageGuard } from './service/auth/SignPageGuard';
import { TestComponent } from './test/test.component';

const routes: Routes = [
  { path: 'products', component: AllProductsComponent, canActivate: [AuthGuardService] },
  { path: 'test', component: TestComponent, canActivate: [AuthGuardService] },
  { path: 'myshop', component: MyShopComponent, canActivate: [AuthGuardService] },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuardService] },
  { path: 'login', component: LoginComponent ,canActivate: [SignPageGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [SignPageGuard] },
  { path: 'product', component: ProductAddComponent },
  {
    path: 'productViewDetails/:productId',
    component: DetailProductComponent,
    canActivate: [AuthGuardService],
    resolve: { product: ProductResolveService },
  },
  {
    path: 'buyProduct/:id',
    component: BuyProductComponent,
    canActivate: [AuthGuardService],
    resolve: { productDetails: ProductResolverService }
  },
  { path: 'cart', component: CartComponent, canActivate: [AuthGuardService] },
  { path: 'orderConfirm', component: OrderConfirmationComponent, canActivate: [AuthGuardService] },
  { path: 'myOrders', component: MyOrdersComponent, canActivate: [AuthGuardService] },
  { path: 'register', component: RegisterComponent },
  { path: 'orderInformation', component: OrderDetailsComponent, canActivate: [AuthGuardService] },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
