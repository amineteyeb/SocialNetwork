import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Product } from 'src/app/model/product.model';
import { Observable } from 'rxjs';
import { UserSeller } from 'src/app/model/US.model';
import { TokenStorageService } from '../token/token-storage.service';
@Injectable({
  providedIn: 'root'
})
export class ShopService {
  private baseUrl = 'http://localhost:8070/social/product';
  private URL = 'http://localhost:8070/social/MyShop'; 
  constructor(private http: HttpClient,private token : TokenStorageService) { }

  public addProduct(product: FormData) {
    return this.http.post<Product>(this.baseUrl+"/add", product);
  }


  getMyProducts(token:any): Observable<Product[]> {
    const options = {
      headers: new HttpHeaders().set(

        'Authorization', `Bearer ${token}`
      ),
    };
    return this.http.get<Product[]>(this.baseUrl+"/myshop",options);
  }
  getMySellerDetails(): Observable<UserSeller> {
    const url = `${this.URL}/getdetailsbyuser`;
    const options = {
      headers: new HttpHeaders().set(
        
        'Authorization', `Bearer ${this.token.getToken()}`
      ),
    };
    return this.http.get<UserSeller>(this.URL+"/getdetailsbyuser",options);
  }
  getProductsExceptMine(token:any): Observable<Product[]> {
    const options = {
      headers: new HttpHeaders().set(

        'Authorization', `Bearer ${token}`
      ),
    };
    return this.http.get<Product[]>(this.baseUrl,options);
  }
}
