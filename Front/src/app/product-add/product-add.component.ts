import { Component,OnInit } from '@angular/core';
import { ShopService } from '../service/shop/shop.service';
import { Product } from '../model/product.model';
import { HttpErrorResponse } from '@angular/common/http';
import { file } from '../model/file.model';
import { DomSanitizer } from "@angular/platform-browser";
import { ActivatedRoute } from "@angular/router";
import { NgForm } from "@angular/forms";
import { AuthService } from '../service/auth/auth.service';
import { TokenStorageService } from '../service/token/token-storage.service';
import { UserSeller } from '../model/US.model';
@Component({
  selector: 'app-product-add',
  templateUrl: './product-add.component.html',
  styleUrls: ['./product-add.component.css']
})
export class ProductAddComponent implements OnInit {
  isNewProduct = true;
  mySellerDetails: UserSeller | null = null;

  product: Product = {
    name: '',
    description: '',
    price: 0,
    productDiscountedPrice:0,
    productImages : [],
    available: false,
    shopId: 0,
    sellerId: 0
  };
  isLoggedIn?: boolean;
  user: any = {}; 
  sellerflag : boolean = false;
  clientflag? : boolean;
 token?:any;
  constructor(
    private productService: ShopService,
    private sanitizer: DomSanitizer,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,private tokenservice:TokenStorageService
  ) {}

  ngOnInit(): void {
  //  this.product = this.activatedRoute.snapshot.data['product'];
  this.token=this.tokenservice.getToken();
    console.log(this.token);
    this.getuser(this.token);
    this.productService.getMySellerDetails().subscribe(
      (data: UserSeller) => {
        this.mySellerDetails = data;
        console.error(this.mySellerDetails);
        // You can now use this.mySellerDetails in your component
      },
      (error) => {
        console.error('Error fetching seller details:', error);
      }
    );
   // if(this.product && this.product.id) {
    //  this.isNewProduct = false;
   // }
  }

  addProduct(productForm: NgForm) {
    this.product.name = productForm.value.name;
    this.product.description = productForm.value.description;
    this.product.price = productForm.value.price;
    this.product.available=true;
    if (this.mySellerDetails) {
   
      this.product.shopId = this.mySellerDetails.shop_id;
    } else { console.log('Error fetching seller details')
      // Handle the case where mySellerDetails is null
      // For example, you can set a default sellerId or show an error message
    }
  
    

    const formData = this.prepareFormDataForProduct(this.product);
    
    this.productService.addProduct(formData).subscribe(
      (response: Product) => {
        productForm.reset();
       this.product.productImages = [];
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  prepareFormDataForProduct(product: Product): FormData {
    
    const uploadImageData = new FormData();
    uploadImageData.append(
      "product",
      new Blob([JSON.stringify(product)], { type: "application/json" })
    );

    for (var i = 0; i < this.product.productImages.length; i++) {
      uploadImageData.append(
        "imageFile",
        this.product.productImages[i].file,
        this.product.productImages[i].file.name
      );
    }
    return uploadImageData;
  }

  onFileSelected(event:any) {
    if (event.target.files) {
      const file = event.target.files[0];
      const fileHandle: file = {
        file: file,
        url: this.sanitizer.bypassSecurityTrustUrl(
          window.URL.createObjectURL(file)
        ),
      };
      this.product.productImages.push(fileHandle);
    }
  }

  removeImages(i: number) {
    this.product.productImages.splice(i, 1);
  }

  fileDropped(fileHandle: file) {
    this.product.productImages.push(fileHandle);
  }
  handleFileDrop(event: Event) {
    if (event instanceof FileList) {
      const file = event[0];
      const fileHandle: file = {
        file: file,
        url: this.sanitizer.bypassSecurityTrustUrl(
          window.URL.createObjectURL(file)
        ),
      };
      this.product.productImages.push(fileHandle);
    }
  }
  getuser(token:string):any  {
    this.authService.getUser(token).subscribe(
      (user: any) => {
       
      //  console.log(JSON.stringify(user) +"haya 3ad");
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
