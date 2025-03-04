import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, HostListener, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth/auth.service';
import { TokenStorageService } from 'src/app/service/token/token-storage.service';
import { UserService } from 'src/app/service/user/user.service';
import { UsersettingService } from 'src/app/service/user/usersetting.service';

@Component({
  selector: 'app-nav',


  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit {
  @Input() showNavbar: boolean = true;
  isLoggedIn?: boolean;
  user: any = {}; 
  isDropdownOpen = false;
  sellerflag : boolean = false;
  clientflag? : boolean;
 token?:any;
 usertheme:any;
 sidebarOpen = false;
    isMobile = window.innerWidth < 768;
  constructor(private userService :UserService,public settings:UsersettingService,private snackBar: MatSnackBar,private authService: AuthService,private tokenservice:TokenStorageService,private router:Router,private ref: ChangeDetectorRef){ }
 
  ngOnInit():void {
    document.getElementById('hamburgerBtn')?.addEventListener('click', this.toggleMenu);
    console.log();
    this.token=this.tokenservice.getToken();
    console.log(this.token);
    this.getuser(this.token);

   this.ref.detectChanges();
  
       // Fetch user data from storage
   //   console.log("useris"+JSON.parse(this.user));
    //  console.log("useris"+this.user.role);
   
    console.log(this.isLoggedIn)// {2}
   
  }
  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
}

@HostListener('window:resize', ['$event'])
onResize(event: any) {
    this.isMobile = event.target.innerWidth < 768;
}
  toggleMenu() {
    const menu = document.getElementById('navMenu');
    menu?.classList.toggle('active');
}
  generateAvatarUrl(username: string): string {
    const length = 1;
    const rounded = true;
    const bold = true;
    const background = 'fe4c50';
    const color = 'ffffff';
    const size = 32;
    return `https://ui-avatars.com/api/?length=${length}&name=${username}&rounded=${rounded}&bold=${bold}&background=%23${background}&color=%23${color}&size=${size}`;
  }
  getuser(token:string):any  {
    this.authService.getUser(token).subscribe(
      (user: any) => {
       
      //  console.log(JSON.stringify(user) +"haya 3ad");
      this.user =user;
    
      console.log(this.user)
      if (this.user.role == "SELLER")
      {
        this.sellerflag=true;
      }
      console.log(user.theme)
    this.usertheme=user.theme;
       // this.tokenStorage.saveUser(user);
        //console.log(JSON.stringify(this.tokenStorage.getUser())+"user from login");
      },
      (error:any) => {
        console.error('Error fetching user data:', error);
      }
    );
    
  }
  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;

    console.log('Dropdown toggled:', this.isDropdownOpen);

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
  SetTheme(click : string): void {
    // Check if the user object exists and has roles array
    if (click == this.usertheme) {
      this.showNotification("theme "+(this.usertheme).toLocaleUpperCase()+" is already set")
    }
    else{
      
      
       this.updateTheme(click);  
      
      
      console.log(this.tokenservice.getUser().theme+"new theme"+click ) ;
      }

  }
  updateTheme(theme:string):void{
  
    const user = this.user;
    
    user.theme = theme.toLocaleUpperCase();
    this.showNotification("Updating To "+theme.toLocaleUpperCase()+" mode")
    this.userService.updateUserSettings(this.user.id,theme).subscribe({
      next: (resp: any) => {
        this.settings.setTheme(theme.toLocaleLowerCase());
    this.usertheme = theme;
    
      this.showNotification("Theme Set To "+(this.settings.getTheme()).toLocaleUpperCase()+" mode")
    
       console.log("updated" + JSON.stringify(resp) + JSON.stringify(this.usertheme))
      },
      error: (err: HttpErrorResponse) => {
        console.error("Error logging:", err);
        if (err.error instanceof ErrorEvent) {
          console.error("Client-side error:", err.error.message);
        } else {
          console.error("Server-side error:", err.error);
        }
      },
    });
  }
  private showNotification(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 4000,
      horizontalPosition: 'end',
      verticalPosition: 'bottom',
    });
  }
}
