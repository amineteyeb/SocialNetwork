import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import Swal from 'sweetalert2';
import { TokenStorageService } from '../token/token-storage.service';


export const SignPageGuard: CanActivateFn = async (route, state) => {
  const sessionManager = inject(TokenStorageService);
  const isLoggedIn = sessionManager.isAuthenticated();

  if (isLoggedIn) {

    Swal.fire({
      title: "You are Already Connected",
      icon: "warning"
    });

    return false;
  } else {
    
    return true;
  }
};
