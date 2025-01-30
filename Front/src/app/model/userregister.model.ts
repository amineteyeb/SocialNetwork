import {Role} from "./role.model";

export class userregister {
 
  firstname: string;
  lastname: string;
  email: string;
 
  password: string;
  role:string;

  constructor() {

    this.firstname = "";
    this.lastname = "";
    this.email = "";
  
    this.password = "";
    this.role = "";
  }
}
