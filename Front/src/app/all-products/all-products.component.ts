import { Component,OnInit } from '@angular/core';
import { ShopService } from '../service/shop/shop.service';
import { ImageProcessingService } from '../service/files/image-processing.service';
import { Router,ActivatedRoute } from '@angular/router';
import { TokenStorageService } from '../service/token/token-storage.service';
import { NONE_TYPE } from '@angular/compiler';
import { NULL } from 'sass';
import { HttpErrorResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Product } from '../model/product.model';
@Component({
  selector: 'app-all-products',
  templateUrl: './all-products.component.html',
  styleUrls: ['./all-products.component.css']
})
export class AllProductsComponent implements OnInit {

  pageNumber: number = 0;

  productDetails : Product[] = [];

  showLoadButton = false;
  token :any ='';

  constructor(private activatedRoute: ActivatedRoute,
    
    private shopS: ShopService,
    private imageProcessingService: ImageProcessingService,
    private router: Router,tokenStorage : TokenStorageService) { this.token=tokenStorage.getToken();}

  ngOnInit(): void {
    this.getAllProducts();
  
  }
  
 /* searchByKeyword(searchkeyword) {
    console.log(searchkeyword);
    this.pageNumber = 0;
    this.productDetails = [];
    this.getAllProducts(searchkeyword);
  }
*/
  public getAllProducts(searchKey: string = "") {
    
    this.shopS.getProductsExceptMine(this.token)
    .pipe(
      map((x: Product[], i) => x.map((product: Product) => this.imageProcessingService.createImages(product)))
    )
    .subscribe(
      (resp: Product[]) => {
        console.log(resp);
        console.log(resp);
       
        resp.forEach(p => this.productDetails.push(p));
      }, (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  public loadMoreProduct() {
    this.pageNumber = this.pageNumber + 1;
    this.getAllProducts();
  }

  showProductDetails(productId: any) {
    this.router.navigate(['/productViewDetails', productId]);
  }
  
}



