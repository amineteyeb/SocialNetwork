// buy-product.component.ts

import { Component, OnInit, Injector, NgZone } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PaymeeService } from '../service/order/orderservice';
import { OrderDetails } from '../model/order-details.model';
import { Product } from '../model/product.model';


@Component({
  selector: 'app-buy-product',
  templateUrl: './buy-product.component.html',
  styleUrls: ['./buy-product.component.css']
})
export class BuyProductComponent implements OnInit {
 urlRedirect : string ='';
  productDetails: Product[] = [];
  orderDetails: OrderDetails = {
    id: 0,
    fullName: '',
    fullAddress: '',
    contactNumber: '',
    alternateContactNumber: '',
    transactionId: '',
    orderProductQuantityList: []
  };

  constructor(
    private activatedRoute: ActivatedRoute,
    private productService: PaymeeService,
    private router: Router,
    private injector: Injector
  ) {}

  ngOnInit(): void {
    const productDetailsData = this.activatedRoute.snapshot.data['productDetails'];

    if (Array.isArray(productDetailsData)) {
      this.productDetails = productDetailsData;
    } else if (typeof productDetailsData === 'object' && productDetailsData !== null) {
      this.productDetails = [productDetailsData];
    } else {
      console.error("Invalid productDetails data:", productDetailsData);
      // Handle the error or provide a default behavior
    }

  

    this.productDetails.forEach((x: any) => {
      if (typeof x.id === 'number') {
        this.orderDetails.orderProductQuantityList.push({ productId: x.id, quantity: 1 });
      }
    });
  }

  createTransactionAndPlaceOrder(orderForm: NgForm): void {
    const amount = this.getCalculatedGrandTotal();
    
    // Update the paymentData based on Paymee API specifications
    const paymentData = {
      "amount": 220.25,
      "note": "Order #123",
      "first_name": "John",
      "last_name": "Doe",
      "email": "test@paymee.tn",
      "phone": "+21611222333",
      "return_url": "https://localhost:4200/products",
      "cancel_url": "https://localhost:4200/products",
      "webhook_url":"https://www.webhook_url.tn",
      "order_id": "244557"
    };

    this.productService.createPayment(paymentData).subscribe(
   
        (resp: any) => {
          if (resp.hasOwnProperty('data') &&
              resp.data.hasOwnProperty('token') &&
              resp.data.hasOwnProperty('amount') &&
              resp.data.hasOwnProperty('payment_url')) {
            // All properties exist
            window.location.href = resp.data.payment_url;
            console.log('token:', resp.data.token);
            console.log('amount:', resp.data.amount);
            console.log('payment_url:', resp.data.payment_url);
          } else {
            // Some properties are missing
            console.log('Some properties are missing');
          }
        },
        (error) => {
          console.log('Error during payment creation: ', error);
        }
     );
    }
  
initiatePayment() {
  const paymentData = {
    "amount": 220.25,
    "note": "Order #123",
    "first_name": "John",
    "last_name": "Doe",
    "email": "test@paymee.tn",
    "phone": "+21611222333",
    "return_url": "https://www.return_url.tn",
    "cancel_url": "https://www.cancel_url.tn",
    "webhook_url": "https://www.webhook_url.tn",
    "order_id": "244557"
  };

  // Call the Paymee service to initiate payment
  const paymentRequest = { /* your payment request data */ };
  this.productService.createPayment(paymentRequest).subscribe(
    response => {
      this.urlRedirect=response.data.payment_url;
      console.log(response.data.payment_url); // Make sure to access 'data' property
      // Handle the response, e.g., redirect to the payment page
      window.location.href = response.data.payment_url; // Access 'data' property
      console.log(this.urlRedirect)
      console.log(this.urlRedirect)
      console.log(this.urlRedirect)
      console.log(this.urlRedirect)
    },
    error => {
      console.log(this.urlRedirect)
      // Handle errors
  
    }
  );
}

  openTransactionModal(response: any, orderForm: NgForm): void {
    if (response && response.orderId && response.key && response.amount && response.currency) {
      const options = {
        order_id: response.orderId,
        key_id: response.key,
        amount: response.amount,
        currency: response.currency,
        name: 'Learn programming yourself',
        description: 'Payment of online shopping',
        image: 'https://cdn.pixabay.com/photo/2023/01/22/13/46/swans-7736415_640.jpg',
        handler: (response: any) => {
          if (response != null && response.key != null) {
            this.processResponse(response, orderForm);
          } else {
            alert("Payment failed..");
          }
        },
        prefill: {
          name: 'LPY',
          email: 'LPY@GMAIL.COM',
          contact: '90909090'
        },
        notes: {
          address: 'Online Shopping'
        },
        theme: {
          color: '#F37254'
        }
      };
    
      const product = this.productDetails.find(product => product.id === this.orderDetails.orderProductQuantityList[0]?.productId);
    
      if (product) {
        // Uncomment the next line if you're using Razorpay
        // var razorPayObject: any = new Razorpay(options);
        // razorPayObject.open();
      } else {
        console.error("Product not found or is null:", product);
      }
    } else {
      console.error("Invalid response object:", response);
    }
  }
  

  processResponse(resp: any, orderForm: NgForm): void {
    this.orderDetails.transactionId = resp.key;
    this.placeOrder(orderForm);
    // Assuming 'response' is your object
if (resp.hasOwnProperty('orderId') &&
resp.hasOwnProperty('currency') &&
resp.hasOwnProperty('amount') &&
resp.hasOwnProperty('key')) {
// All properties exist
console.log('orderId:', resp.orderId);
console.log('currency:', resp.currency);
console.log('amount:', resp.amount);
console.log('key:', resp.key);
} else {
// Some properties are missing
console.log('Some properties are missing');
}

  }

  placeOrder(orderForm: NgForm): void {
    this.productService.createPayment(this.orderDetails).subscribe(
      (resp) => {
        console.log(resp);
        orderForm.reset();

        const ngZone = this.injector.get(NgZone);
        ngZone.run(() => {
          this.router.navigate(["/home"]);
        });
      },
      (err) => {
        console.log(err);
      }
    );
  }

  getCalculatedGrandTotal(): number {
    let grandTotal = 0;

    this.orderDetails.orderProductQuantityList.forEach((productQuantity: any) => {
      const price = this.productDetails.find(product => product.id === productQuantity.productId)?.price;
      grandTotal += (price || 0) * productQuantity.quantity;
    });

    return grandTotal;
  }

  getQuantityForProduct(productId: any): number {
    const filteredProduct = this.orderDetails.orderProductQuantityList.find(
      (productQuantity) => productQuantity.productId === productId
    );

    return filteredProduct?.quantity || 1;
  }

  getCalculatedTotal(productId: any, price: any): number {
    const filteredProduct = this.orderDetails.orderProductQuantityList.find(
      (productQuantity) => productQuantity.productId === productId
    );

    return (filteredProduct?.quantity || 0) * price;
  }

  onQuantityChanged(q: any, productId: any): void {
    const orderProduct = this.orderDetails.orderProductQuantityList.find(
      (productQuantity: any) => productQuantity.productId === productId
    );

    if (orderProduct) {
      orderProduct.quantity = q;
    }
  }
}
