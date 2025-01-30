import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductServiceService } from '../service/product/product-service.service';
import { AuthService } from '../service/auth/auth.service';
import { TokenStorageService } from '../service/token/token-storage.service';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  displayedColumns: string[] = ['Name', 'Description', 'Price', 'Discounted Price', 'Action'];
  user: any = {}; 
  sellerflag : boolean = false;
  clientflag? : boolean;
  token?:any;
  cartDetails: any[] = [];

  constructor(private productService: ProductServiceService,
    private authService: AuthService,private tokenservice:TokenStorageService,
    private router: Router) { }

  ngOnInit(): void {
 
    
    this.token=this.tokenservice.getToken();
    this.getuser(this.token);
   
   
  }

  delete(cartId:number) {
    console.log(cartId);
    this.productService.deleteCartItem(cartId).subscribe(
      (resp) => {
        console.log(resp);
        this.getCartDetails(this.user.id);
      }, (err) => {
        console.log(err);
      }
    );
  }

  getCartDetails(userid:number) {
    this.productService.getCartDetails(userid).subscribe(
      (response:any) => {
        console.log(response);
        this.cartDetails = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }
  getuser(token:string):any  {
    this.authService.getUser(token).subscribe(
      (user: any) => {
       
      // console.log(JSON.stringify(user) +"haya 3ad");
      this.user =user;
    this.getCartDetails(this.user.id);
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
  checkout() {
    
    this.router.navigate(['/buyProduct', {
      isSingleProductCheckout: false, id: 0
    }]);

    // this.productService.getProductDetails(false, 0).subscribe(
    //   (resp) => {
    //     console.log(resp);
    //   }, (err) => {
    //     console.log(err);
    //   }
    // );
  }
}
