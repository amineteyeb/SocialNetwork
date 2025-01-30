import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth/auth.service';
import { TokenStorageService } from 'src/app/service/token/token-storage.service';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit {
  isLoggedIn?: boolean;
  user: any = {}; 
  sellerflag : boolean = false;
  clientflag? : boolean;
 token?:any;
  constructor(private authService: AuthService,private tokenservice:TokenStorageService,private router:Router,private ref: ChangeDetectorRef){ }
 
  ngOnInit():void {
    console.log();
    this.token=this.tokenservice.getToken();
    console.log(this.token);
    this.getuser(this.token);
  
    //this.ref.detectChanges();
  
       // Fetch user data from storage
   //   console.log("useris"+JSON.parse(this.user));
    //  console.log("useris"+this.user.role);
   
    console.log(this.isLoggedIn)// {2}
   
  }
  getuser(token:string):any  {
    this.authService.getUser(token).subscribe(
      (user: any) => {
       
      //  console.log(JSON.stringify(user) +"haya 3ad");
      this.user =user;
      if (this.user.role == "SELLER")
      {
        this.sellerflag=true;
      }
       // this.tokenStorage.saveUser(user);
        //console.log(JSON.stringify(this.tokenStorage.getUser())+"user from login");
      },
      (error:any) => {
        console.error('Error fetching user data:', error);
      }
    );
    
  }
  onLogout(){
    this.tokenservice.signOut();  
    this.authService.logout();  
                      // {3}
  }
  navigateToMyShop() {
    this.router.navigate(['/myshop']);
  }
  navigateToProductsOwner() {
    this.router.navigate(['/products']);
  }
}
