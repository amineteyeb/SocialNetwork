
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { MyOrderDetails } from 'src/app/model/order.model';
import { OrderDetails } from 'src/app/model/order-details.model';
import { Product } from 'src/app/model/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductServiceService {
  constructor(private httpClient: HttpClient) { }
  private apiUrl = 'http://localhost:8070/social';
  public createTransaction(amount:any) {
    return this.httpClient.get("http://localhost:8070/social/createTransaction/"+amount);
  }

  public markAsDelivered(orderId:any) {
      return this.httpClient.get("http://localhost:8070/social/markOrderAsDelivered/"+orderId)
  }

  public getAllOrderDetailsForAdmin(status: string): Observable<MyOrderDetails[]> {
    return this.httpClient.get<MyOrderDetails[]>("http://localhost:8070/social/getAllOrderDetails/"+status);
  }

  public getMyOrders(): Observable<MyOrderDetails[]> {
    return this.httpClient.get<MyOrderDetails[]>("http://localhost:8070/social/getOrderDetails");
  }

  public deleteCartItem(cartId:any) {
    return this.httpClient.delete("http://localhost:8070/social/deleteCartItem/"+cartId);
  }

  public addProduct(product: FormData) {
    return this.httpClient.post<Product>("http://localhost:8070/social/product/add", product);
  }

  public getAllProducts(pageNumber:any, searchKeyword: string = "") {
    return this.httpClient.get<Product[]>("http://localhost:8070/social/product/products");
  }

  public getProductDetailsById(productId:any) {
    return this.httpClient.get<Product>("http://localhost:8070/social/product/getProductDetailsById/"+productId);
  }

  public deleteProduct(productId: number) {
    return this.httpClient.delete("http://localhost:8070/social/product/deleteProductDetails/"+productId);
  }

  public getProductDetails(isSingleProductCheckout:any, productId:any) {
    return this.httpClient.get<Product[]>("http://localhost:8070/social/product/getProductDetails/"+isSingleProductCheckout+"/"+productId);
  }

  public placeOrder(orderDetails: OrderDetails, isCartCheckout:any) {
    return this.httpClient.post("http://localhost:8070/social/placeOrder/"+isCartCheckout, orderDetails);
  }


  addToCart(productId: number,userID:number): Observable<any> {
   
    const fullUrl = `http://localhost:8070/social/addToCart/${productId}/${userID}`;
    return this.httpClient.get(fullUrl);
  }

  public getCartDetails(userID: number): Observable<any> {
    const fullUrl = `${this.apiUrl}/getCartDetails/${userID}`;
    return this.httpClient.get(fullUrl);
  }

  private baseUrl = 'http://localhost:8070/social/api/v1';

 


}
