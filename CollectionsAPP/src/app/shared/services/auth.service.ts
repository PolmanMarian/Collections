import { Injectable } from '@angular/core';
import {JavaHttpService} from "../connection/http/java-http.service";
import {AuthRequest} from "../../internal-models/auth-request";
import {Observable} from "rxjs";
import {LoginResponseDto} from "../connection/models/login-response.dto";
import {ResponseMessage} from "../connection/models/response-message";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: JavaHttpService) {}

  loginPostRequest(loginRequest: AuthRequest): Observable<LoginResponseDto | String> {
    return this.http.post<LoginResponseDto, AuthRequest>(`/auth/login`, loginRequest);
  }

  registerPostRequest(registerRequest: AuthRequest): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage, AuthRequest>(`/auth/register`,registerRequest);
  }

}
