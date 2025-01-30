import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { ProductServiceService } from '../product/product-service.service';

@Injectable({
  providedIn: 'root'
})
export class ProductResolverService implements Resolve<any> {
  constructor(private productService: ProductServiceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    // Fetch product details using productService
    // You can extract productId from route.params
    const productId = route.params['id'];
    return this.productService.getProductDetailsById(productId);
  }
}