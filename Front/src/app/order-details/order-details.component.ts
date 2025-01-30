import { Component, OnInit } from '@angular/core';
import { ProductServiceService } from '../service/product/product-service.service';
import { MyOrderDetails } from '../model/order.model';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  displayedColumns: string[] = ['Id', 'Product Name', 'Name', 'Address', 'Contact No.', 'Status', 'Action'];

  dataSource!: MyOrderDetails[];
  status: string = 'All';

  constructor(private productService: ProductServiceService) { }

  ngOnInit(): void {
    this.getAllOrderDetailsForAdmin(this.status);
  }

 

  getAllOrderDetailsForAdmin(statusParameter: string) {
    this.productService.getAllOrderDetailsForAdmin(statusParameter).subscribe(
      (resp: MyOrderDetails[]) => {
        this.dataSource = resp;
        console.log(resp);
      }, (error) => {
        console.log(error);
      }
    );
  }
  

  markAsDelivered(orderId:number) {
    console.log(orderId);
    this.productService.markAsDelivered(orderId).subscribe(
      (response) => {
        this.getAllOrderDetailsForAdmin(this.status);
        console.log(response);
      }, (error) => {
        console.log(error);
      }
    );
  }

}
