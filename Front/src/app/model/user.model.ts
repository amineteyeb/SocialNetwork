import {Role} from "./role.model";

export interface User {
  id: number;
  firstname: string;
  lastname: string;
  email: string;
  theme:string;
  password: string;
  role:string;

}
