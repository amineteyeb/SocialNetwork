import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { TokenStorageService } from '../token/token-storage.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private tokenStorageService:TokenStorageService){}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req = req.clone({
      setHeaders: {
       
      
        'Authorization': `Bearer ${this.tokenStorageService.getToken()}`,
      },
    });

    return next.handle(req);
  }
}