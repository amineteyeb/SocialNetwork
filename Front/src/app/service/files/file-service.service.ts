import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { fileServer } from 'src/environment/fileServer';
import { TokenStorageService } from '../token/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class FileServiceService {


    constructor(private http: HttpClient, private ts:TokenStorageService) {}
  
    // Upload Single File
    uploadSingleFile(file: File, token: string): Observable<any> {
      const formData: FormData = new FormData();
      formData.append('file', file);
  
      // Set JWT token in the request header
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
      return this.http.post(`${fileServer.BASE_URL}/upload-single`, formData, { headers });
    }
  
    // Upload Multiple Files
    uploadMultipleFiles(files: File[], token: string): Observable<any> {
      const formData: FormData = new FormData();
      files.forEach(file => formData.append('files', file));
  
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
      return this.http.post(`${fileServer.BASE_URL}/upload-multiple`, formData, { headers });
    }
  
    // Stream Video File
    streamVideo(filename: string): Observable<Blob> {
      return this.http.get(`${fileServer.BASE_URL}/video-stream/${filename}`, {
        responseType: 'blob',
      });
    }
  
  
}
