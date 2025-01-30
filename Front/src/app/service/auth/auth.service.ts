import { Injectable } from '@angular/core';
import {HttpClient,HttpHeaders} from "@angular/common/http";
import {User} from "../../model/user.model";
import {Observable} from "rxjs";
import { userregister } from 'src/app/model/userregister.model';
import { Router } from '@angular/router';
import { TokenStorageService } from '../token/token-storage.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  URL = 'http://localhost:8070/social/auth'


  constructor(private httpClient: HttpClient,private router: Router,private tokenstorage:TokenStorageService) {
  }
    register(user: userregister): Observable<any>{
      const body = JSON.stringify(user);
      const options = {
        headers: new HttpHeaders().set(
          'Content-type',
          'application/json'
        ),
      };
     // options.headers.append("Access-Control-Allow-Origin", "*")
      return this.httpClient.post(this.URL+'/register',body,options);
    }
    login(credentials: { email: string, password: string }): Observable<any> {
      const body = JSON.stringify(credentials);
      const options = {
        headers: new HttpHeaders().set(
          'Content-type',
          'application/json'
        ),
      };
     
      
      // Optionally, you can set the Access-Control-Allow-Origin header here if needed
      // options.headers.append("Access-Control-Allow-Origin", "*");
    
      return this.httpClient.post(this.URL + '/authenticate', body, options);
    }
  getUser(token: any) :any {
    const options = {
      headers: new HttpHeaders().set(
        'Authorization', `Bearer ${token}`
      ),
    };
      return this.httpClient.get<any>(this.URL + '/getuser',options);
    }

  logout() {
    this.httpClient.post(`${this.URL}/logout`, {});
    return   this.router.navigate(['/login']);
  }

  mailReset(email: string): Observable<any> {
    const body = { email: email };
    return this.httpClient.post(this.URL+'/oauth/forgetPassword', body);
  }

  resetPassword(token: string, password: string): Observable<any> {
    const url = `/oauth/resetPassword/${token}`;
    const body = { password }; // create an object with password field
    return this.httpClient.post(this.URL+url, body);
  }

}
