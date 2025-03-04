import { Component, ViewEncapsulation } from '@angular/core';
import { AuthService } from '../service/auth/auth.service'; // Import your AuthService
import { userregister } from '../model/userregister.model';
import { Router } from '@angular/router';
import { AlertService } from '../service/alert/alert.service';

@Component({
  selector: 'app-register', // Make sure this selector matches the HTML element where you want to render this component
  templateUrl: './register.component.html', // Replace with the correct path to your HTML template
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.ShadowDom
})
export class RegisterComponent {
  signUpFormModel: userregister = {
    firstname: '',
    lastname: '',
    email: '',
    password: '',
    role: '', // Initialize to 'SELLER' or 'CLIENT'
  };

  constructor(
    private authService: AuthService,private alertservice:AlertService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.authService.register(this.signUpFormModel).subscribe({
      next: (data) => {
        // Registration successful, you can navigate to another page here if needed
        console.log('Registration successful:', data);
        this.router.navigate(['/login']); // Navigate to login page
      },
      error: (error) => {
        this.alertservice.error(error);
        // Registration failed
        console.error('Registration failed:', error);
      },
    });
  }
}
