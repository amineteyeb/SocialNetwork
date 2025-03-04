import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../../model/user.model";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  URL = 'http://localhost:8070/social'

  constructor(private httpClient: HttpClient) { }
  getUsers():Observable<any[]>{
    return this.httpClient.get<any>(this.URL+"/users");
  }
  updateUser(user:User):Observable<User>{
    return this.httpClient.put<User>(this.URL+"user/update/"+user.id,user);
  }
  deleteUser(id: number): Observable<any> {
    return this.httpClient.delete(this.URL + "/deleteUser/"+id);
  }

  getUserById(id: number): Observable<User> {
    const url = `${this.URL}/user/${id}`;
    return this.httpClient.get<User>(url);
  }
  updateUserSettings(userId: string, theme: string): Observable<any> {
    const url = `${this.URL}/api/user/updateTheme/${userId}`; // Use path variable for userId
    return this.httpClient.put<any>(url, theme); // Send theme in the request body
}

}
