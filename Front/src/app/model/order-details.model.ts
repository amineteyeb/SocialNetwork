import { OrderQuantity } from "./order-quantity.model";

export interface OrderDetails {
    id:number;
    fullName: string;
    fullAddress: string;
    contactNumber: string;
    alternateContactNumber: string;
    transactionId: string,
    orderProductQuantityList: OrderQuantity[];
}