import { Component } from '@angular/core';
import { AuthService } from '../service/auth/auth.service';
import { TokenStorageService } from '../service/token/token-storage.service';
import { Router } from '@angular/router';
import { Token } from '@angular/compiler';
import { User } from '../model/user.model';
import { empty } from 'rxjs';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent  {
 
  connectedUserinfo :any ={};
  form: any = {
    email: null,
    password: null
  };
  signUpFormModel: any = {
    firstname: null,
    lastname: null,
    email: null,
    password: null,
    role: null // Initialize to null or set a default value
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string = '';
token : string ='' ;


  constructor(private authService: AuthService,
              private tokenStorage: TokenStorageService,
              private router : Router,
              ) {
  }
  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  onSubmit(): void {
    const { email, password } = this.form;

  const credentials = {
    email: email,
    password: password
  };

  this.authService.login(credentials).subscribe({
    


      next: (data) => {
        this.tokenStorage.saveToken(data.access_token);
       
        
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        console.log("type is"+data.access_token);
      
     
       
        console.log("user is"+JSON.stringify(this.tokenStorage.getUser()));
      // console.log(this.connectedUser+"this is the user")
        console.log(data.access_token)
      
      },
      error: (err) => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      },
      complete: () => {
      
       this.redirectToHome();
      
       alert("Welcome");
     
      }
    });
   
  }
 
  getuser(token:string):any  {
    this.authService.getUser(token).subscribe(
      (user: any) => {
       
      //  console.log(JSON.stringify(user) +"haya 3ad");
       
        this.tokenStorage.saveUser(user);
        this.connectedUserinfo=user;
        //console.log(JSON.stringify(this.tokenStorage.getUser())+"user from login");
      },
      (error:any) => {
        console.error('Error fetching user data:', error);
      }
    );
    
  }
  
  redirectToHome(): void {
   // console.log("user is"+this.connectedUserinfo)
   
    this.router.navigate(["/home"])
  }
  redirectToFirstTime(): void {
    // console.log("user is"+this.connectedUserinfo)
    
     this.router.navigate(["/home"])
   }
 

}
