// detail-product.component.ts

import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductServiceService } from '../service/product/product-service.service';
import { AuthService } from '../service/auth/auth.service';
import { TokenStorageService } from '../service/token/token-storage.service';

@Component({
  selector: 'app-detail-product',
  templateUrl: './detail-product.component.html',
  styleUrls: ['./detail-product.component.css']
})
export class DetailProductComponent implements OnInit {
  selectedProductIndex = 0;
  product!: Product;
  user: any = {}; 
  sellerflag : boolean = false;
  clientflag? : boolean;
  token?:any;
  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private productService: ProductServiceService,
    private authService: AuthService,private tokenservice:TokenStorageService,
  ) {}

  ngOnInit(): void {
    this.product = this.activatedRoute.snapshot.data['product'];
    console.log(this.product);
    
    this.token=this.tokenservice.getToken();
    this.getuser(this.token);
      
    console.log(this.token);
 
  }

  addToCart(productId: number | undefined,user:number) {
    if (productId !== undefined) {
      console.log(productId);
      console.log(this.user.id);
      console.log(this.token);
      this.productService.addToCart(productId,this.user.id).subscribe(
        (response: any) => {
          console.log(response);
        },
        (error: any) => {
          console.log(error);
        }
      );
    }
  }

  changeIndex(index: number) {
    this.selectedProductIndex = index;
  }

  buyProduct(productId: number | undefined) {
    if (productId !== undefined) {
      this.router.navigate(['/buyProduct', productId], {
        queryParams: { isSingleProductCheckout: true }
      });
    }
  }
  getuser(token:string):any  {
    this.authService.getUser(token).subscribe(
      (user: any) => {
       
      // console.log(JSON.stringify(user) +"haya 3ad");
      this.user =user;
      if (this.user.role == "SELLER")
      {
        this.sellerflag=true;
      }
       // this.tokenStorage.saveUser(user);
        //console.log(JSON.stringify(this.tokenStorage.getUser())+"user from login");
      },
      (error:any) => {
        console.error('Error fetching user data:', error);
      }
    );
    
  }
}
