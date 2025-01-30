import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class PaymeeService {
  private apiUrl = 'http://localhost:8070/social/api/payments/create';

  constructor(private http: HttpClient) {}

  createPayment(paymentRequest: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    return this.http.post(this.apiUrl, paymentRequest, { headers, observe: 'response' }).pipe(
      map((response: HttpResponse<any>) => {
        // Check the content type before parsing
        const contentType = response.headers.get('Content-Type');
        if (contentType && contentType.includes('application/json')) {
          return response.body;
        } else {
          // Handle non-JSON response, e.g., display an error message
          console.error('Unexpected response content type:', contentType);
          return null; // or handle it differently
        }
      }),
      catchError((error) => {
        console.error('Error during payment creation:', error);
        return throwError(error);
      })
    );
  }
}
