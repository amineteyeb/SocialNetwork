import {Role} from "./role.model";

export class User {
  id: number;
  firstname: string;
  lastname: string;
  email: string;
 
  password: string;
  role:string;

  constructor() {
    this.id = 0;
    this.firstname = "";
    this.lastname = "";
    this.email = "";
  
    this.password = "";
    this.role = "";
  }
}
