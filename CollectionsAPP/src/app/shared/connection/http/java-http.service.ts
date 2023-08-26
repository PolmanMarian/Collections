import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpBaseService } from './http-base.service';
import {environment} from "../../../../environments/environment.development";

@Injectable({providedIn: 'root'})
export class JavaHttpService extends HttpBaseService {

  constructor(
    protected override http: HttpClient) {
    super(http);
  }

  protected getBasePath(): string {
    return environment.apiBasUrl;
  }

  public getHeaders() {
      const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8')
      return headers;
  }

}
