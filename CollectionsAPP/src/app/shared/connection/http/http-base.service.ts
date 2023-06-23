import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {SessionService} from "../../services/session.service";

export abstract class HttpBaseService {

  constructor(protected http: HttpClient) { }

  protected abstract getBasePath(): string;

  public get<T>(entityPath: string): Observable<T> {
    const path = `${this.getBasePath()}${entityPath}`;
    console.log(path)
    return this.http.get<T>(path, {headers: new HttpHeaders({
      'Authorization' : `Bearer ${SessionService.getInstance().getCurrentSession().authToken}`,
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        'Access-Control-Allow-Headers' : 'Origin, X-Requested-With, Content-Type, Accept'
      })});
  }

  public post<T, U>(entityPath: string, body: U): Observable<T> {
    const path = `${this.getBasePath()}${entityPath}`;
    console.log(path);
    return this.http.post<T>(path, body,{headers: new HttpHeaders({
        'Authorization' : `Bearer ${SessionService.getInstance().getCurrentSession().authToken}`,
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        'Access-Control-Allow-Headers' : 'Origin, X-Requested-With, Content-Type, Accept'
      })});
  }

  public put<T, U>(entityPath: string, body: U): Observable<T> {
    const path = `${this.getBasePath()}${entityPath}`;
    return this.http.put<T>(path, body);
  }

  public delete<T>(entityPath: string): Observable<T> {
    const path = `${this.getBasePath()}${entityPath}`;
    return this.http.delete<T>(path);
  }

}
