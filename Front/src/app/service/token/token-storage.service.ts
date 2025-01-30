import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Subject } from 'rxjs';
const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  public _authenticatedUser: any;
  set user(data) {
    this._authenticatedUser = data
}
private _listners = new Subject<any>();
get user() {
    return this._authenticatedUser;
}
  constructor() { }
  public jwtHelper: JwtHelperService = new JwtHelperService();
  signOut():void {
    window.sessionStorage.clear();
   this._authenticatedUser ='';
  }
  listen(): Observable<any> {
    return this._listners.asObservable();
 }


  public saveToken(token : string) : void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY,token);
  }
  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }
  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return {};
  }

  public isAuthenticated(): boolean {
    const token = this.getToken();
    console.log('Token:', token);
  
    if (token) {
      const isExpired = this.jwtHelper.isTokenExpired(token);
      console.log('Is Token Expired:', isExpired);
      return !isExpired;
    }
  
    return false;
  }
  
}
