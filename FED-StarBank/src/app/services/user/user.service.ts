import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { SignupUserRequest } from '../../../models/interfaces/user/SignupUserRequest';
import { Observable } from 'rxjs';
import { SignupUserResponse } from '../../../models/interfaces/user/SignupUserResponse';
import { AuthRequest } from '../../../models/interfaces/auth/AuthRequest';
import { AuthResponse } from '../../../models/interfaces/auth/AuthResponse';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private API_URL = environment.API_URL;
  constructor(
    private http: HttpClient,
  ) { }

  signupUser(requestData: SignupUserRequest): Observable<SignupUserResponse> {
    const url = `${this.API_URL}/starbank/users`;
    return this.http.post<SignupUserResponse>(url, requestData);
  }

  authUser(requestDatas: AuthRequest):Observable<AuthResponse> {
    const url = `${this.API_URL}/starbank/auth`;
    return this.http.post<AuthResponse>(url, requestDatas);

  }
  getUserByEmail(email: string): Observable<SignupUserResponse> {
    const url = `${this.API_URL}/starbank/users/email/${email}`;
    return this.http.get<SignupUserResponse>(url);
  }
}
