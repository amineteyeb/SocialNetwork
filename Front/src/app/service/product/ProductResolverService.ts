import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Product } from '../../model/product.model';
import { ProductServiceService } from './product-service.service';
import { ImageProcessingService } from '../../image-processing.service'
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: "root",
})
export class ProductResolveService implements Resolve<Product> {
  constructor(private productService: ProductServiceService,
    private imageProcessingService: ImageProcessingService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<Product> {
    const id = route.paramMap.get("productId");

    if (id) {
      //then we have to fetch details from backend
       return this.productService.getProductDetailsById(id)
              .pipe(
                map(p => this.imageProcessingService.createImages(p))
              );
    } else {
      // return empty product observable.
      return of(this.getProductDetails());
    }
  }

  getProductDetails() {
    return {
    name: '',
    description: '',
    price: 0,
    productDiscountedPrice:0,
    productImages : [],
    available: false,
    shopId: 0,
    sellerId: 0
    };
  }
}
