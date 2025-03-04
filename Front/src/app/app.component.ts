import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './service/token/token-storage.service';
import { Router, NavigationEnd } from '@angular/router';
import { UsersettingService } from './service/user/usersetting.service';

@Component({
  selector: 'app-root',

  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  
})
export class AppComponent implements OnInit {
  isTranslucent: boolean = false;
  isNavbarVisible: boolean = false;
  title: any;

  constructor(
    private router: Router,
    private authService: TokenStorageService,
    public themeService: UsersettingService
  ) {}

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Determine whether to show the navbar based on the authentication status and current route
        const currentUrl = event.url;
        const isLoggedIn = this.authService.isAuthenticated();


        // Hide the navbar on the admin dashboard and landing page, regardless of authentication status
        this.isNavbarVisible = isLoggedIn && !(
          currentUrl.includes('/admin') ||
          currentUrl.includes('/landingpage')||
          currentUrl.includes('/login') ||
          currentUrl.includes('/register')
        );
      }
    });
    // Initialize the theme from localStorage or system preference
    this.initializeTheme();

    // Listen for system theme changes
    this.listenForSystemThemeChanges();
  }

  // Initialize the theme
  private initializeTheme(): void {
    const storedTheme = this.themeService.getTheme();
    console.log(storedTheme)

    const theme = storedTheme ; // Use stored theme if available, otherwise use system preference
    this.themeService.setTheme(theme);
  }

  // Listen for system theme changes
  private listenForSystemThemeChanges(): void {
    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', (e) => {
      const newTheme = e.matches ? 'dark' : 'light';
      this.themeService.setTheme(newTheme);
    });
  }
}
