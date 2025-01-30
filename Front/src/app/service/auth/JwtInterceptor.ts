import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../token/token-storage.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor( private tokenStorage: TokenStorageService,) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token =  this.tokenStorage.getToken(); // Retrieve the token from your secure storage (e.g., localStorage)
    if (token) {
      request = request.clone({
          setHeaders: {
              'Content-Type': 'application/json; charset=utf-8',
              Accept: 'application/json',
              Authorization: `Bearer ${token}`
          }
      });
  } else {
    request = request.clone({
          setHeaders: {
              'Content-Type': 'application/json; charset=utf-8',
              Accept: 'application/json'
          }
      });
  }

  return next.handle(request);
  
  }}
