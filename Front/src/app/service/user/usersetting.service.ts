import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsersettingService {


  private readonly THEME_KEY = 'theme';
  private readonly LANGUAGE_KEY = 'user-language';

  constructor() {}

  getTheme(): string {
    return sessionStorage.getItem(this.THEME_KEY) || 'light';
  }

  setTheme(theme: string): void {
    sessionStorage.setItem(this.THEME_KEY, theme);
    this.applyTheme(theme);
  }

  private applyTheme(theme: string): void {
    document.documentElement.setAttribute('data-bs-theme', theme);
  }

  public setLanguage(language: string) {
    sessionStorage.setItem(this.LANGUAGE_KEY, language);
    // Here you would typically implement logic to change the language globally
    // For demonstration, we'll just store it in session storage
  }

  public getLanguage(): string {
    return sessionStorage.getItem(this.LANGUAGE_KEY) || 'en'; // Default language is English
  }
}
