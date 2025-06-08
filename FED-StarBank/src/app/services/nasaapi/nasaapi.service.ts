import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NasaapiService {

private apiKey = 'lP2iHuJuKtd2hlJWxpwFfSCAFE2bTMc7rCghVUm2';
  private baseUrl = 'https://api.nasa.gov/planetary/apod';

  constructor(private http: HttpClient) {}

  getPhotoOfTheDay(date?: string): Observable<any> {
    const url = date
      ? `${this.baseUrl}?api_key=${this.apiKey}&date=${date}`
      : `${this.baseUrl}?api_key=${this.apiKey}`;
    return this.http.get(url);
  }

}
