import { file } from "./file.model";

export interface Product {
  id?: number ;
  name: string;
  description: string;
  price: number;
  productDiscountedPrice :number;
  productImages: file[];
  available: boolean;
  shopId: number;
  sellerId: number;

}
