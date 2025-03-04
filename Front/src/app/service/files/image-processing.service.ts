import { Injectable } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { file } from '../../model/file.model';
import { Product } from '../../model/product.model';

@Injectable({
  providedIn: 'root'
})
export class ImageProcessingService {

  constructor(private sanitizer: DomSanitizer) { }

  public createImages(product: Product) {
    if (!product || !product.productImages) {
      // Handle the case where product or product.image is undefined
      return product;
    }
  
    const productImages: any[] = product.productImages;
  
    const productImagesToFileHandle: file[] = [];
  
    for (let i = 0; i < productImages.length; i++) {
      const imageFileData = productImages[i];
  
      const imageBlob = this.dataURItoBlob(imageFileData.picByte, imageFileData.type);
  
      const imageFile = new File([imageBlob], imageFileData.name, { type: imageFileData.type });
  
      const finalFileHandle: file = {
        file: imageFile,
        url: this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(imageFile)),
      };
  
      productImagesToFileHandle.push(finalFileHandle);
    }
  
    product.productImages = productImagesToFileHandle;
    return product;
  }
  
  public dataURItoBlob(picBytes: string, imageType: string) {
    const byteString = window.atob(picBytes);
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const int8Array = new Uint8Array(arrayBuffer);

    for(let i = 0; i < byteString.length; i++) {
      int8Array[i] = byteString.charCodeAt(i);
    }

    const blob = new Blob([int8Array], { type: imageType});
    return blob;
}
}
