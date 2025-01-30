import { Component, OnInit } from '@angular/core';
import { MyOrderDetails } from '../model/order.model';
import { ProductServiceService } from '../service/product/product-service.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {

  displayedColumns = ["Name", "Address", "Contact No.", "Amount", "Status"];

  myOrderDetails: MyOrderDetails[] = [];

  constructor(private productService: ProductServiceService) { }

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails() {
    this.productService.getMyOrders().subscribe(
      (resp: MyOrderDetails[]) => {
        console.log(resp);
        this.myOrderDetails = resp;
      }, (err)=> {
        console.log(err);
      }
    );
  }

}
