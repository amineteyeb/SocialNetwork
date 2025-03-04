import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from 'src/app/model/user.model';
import { AuthService } from 'src/app/service/auth/auth.service';
import { TokenStorageService } from 'src/app/service/token/token-storage.service';

@Component({
  selector: 'app-sidebar',


  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
  @Input() userRole : string ='';
  @Input() userName : string ='';
  @Input() sellerFlag : Boolean =false;
  user:any={};
  @Output() sidebarClose = new EventEmitter<void>();
  constructor(private authService:AuthService,private tokenservice:TokenStorageService){

  }
  ngOnInit():void {
  
    this.user=this.tokenservice.getUser();
    console.log(this.user);
  


  }
  onLogout(){
    this.tokenservice.signOut();  
    this.authService.logout();  
                      // {3}
  }
  generateAvatarUrl(username: string): string {
    const length = 1;
    const rounded = true;
    const bold = true;
    const background = 'f52f6a';
    const color = 'ffffff';
    const size = 32;
    return `https://ui-avatars.com/api/?length=${length}&name=${username}&rounded=${rounded}&bold=${bold}&background=%23${background}&color=%23${color}&size=${size}`;
  }
  closeSidebar() {
    this.sidebarClose.emit(); // Notify navbar to close sidebar
  }
}
