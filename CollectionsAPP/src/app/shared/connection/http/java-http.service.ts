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

  // public getAllRecyclingCenters(): Observable<CenterDto[]> {
  //   return this.http.get<CenterDto[]>(`${this.getBasePath()}/recyclingCenter/find/all`)
  // }
  //
  // public addProduct(productDTO: ProductDto): Observable<any> {
  //   return this.http.post<ProductDto>(`${this.getBasePath()}/product/insertProduct`, productDTO);
  // }
  //
  // public editProduct(productDTO: ProductDto): Observable<string> {
  //   return this.http.post<string>(`${this.getBasePath()}/product/update`, productDTO);
  // }
  //
  // public deleteProduct(id: number): Observable<string> {
  //   return this.http.post<string>(`${this.getBasePath()}/product/deleteProduct`, id)
  // }
  //
  // public getProducts(): Observable<ProductDto[]> {
  //   return this.http.get<ProductDto[]>(`${this.getBasePath()}/product/getAllProducts`);
  // }
  //
  // public getCategories(): Observable<CategoryDto[]> {
  //   return this.http.get<CategoryDto[]>(`${this.getBasePath()}/category/getAllCategories`);
  // }
  //
  // public validateCategory(categoryDto: CategoryDto): string[] {
  //   let errors: string[] = [];
  //   if (categoryDto.name.trim() === '') {
  //     errors.push('Name field is mandatory');
  //   }
  //   return errors;
  // }
  //
  // public postProductCategory(productCategoryDTO: CategoryDto): Observable<number> {
  //   return this.http.post<number>(`${this.getBasePath()}/category/insertCategory`, productCategoryDTO);
  // }
  //
  // public deleteCategory(id: number): Observable<string> {
  //   return this.http.post<string>(`${this.getBasePath()}/category/delete`, id);
  // }
  //
  // public updateCategory(category: CategoryDto): Observable<string> {
  //   return this.http.post<string>(`${this.getBasePath()}/category/update`, category);
  // }
  //
  // public getAllCenters(): Observable<CenterDto[]> {
  //   return this.http.get<CenterDto[]>(`${this.getBasePath()}/recyclingCenter/getAllCenters`);
  // }
  //
  // public deleteCenter(id: number): Observable<string> {
  //   return this.http.post<string>(`${this.getBasePath()}/recyclingCenter/deleteCenter`, id);
  // }
  //
  // public updateCenter(centerDto: CenterDto): Observable<string> {
  //   return this.http.post<string>(`${this.getBasePath()}/recyclingCenter/update`, centerDto);
  // }
  //
  // public postRecyclingCenter(centerDto: CenterDto): Observable<number> {
  //   return this.http.post<number>(
  //     `${this.getBasePath()}/recyclingCenter/insertCenter`,
  //     centerDto
  //   );
  // }
  //
  // public postAppointment(appointment: AppointmentDto): Observable<string> {
  //   return this.http.post<string>(`${this.getBasePath()}/user/makeAppointment`, appointment);
  // }
  //
  // public getAppointmentsById(id: number): Observable<AppointmentDto[]> {
  //   return this.http.get<AppointmentDto[]>(`${this.getBasePath()}/appointment/getUserAppointments/${id}`);
  // }
  //
  // public getAppointments(): Observable<AppointmentDto[]> {
  //   return this.http.get<AppointmentDto[]>(`${this.getBasePath()}/appointment/getAllAppointments`)
  // }
  //
  // public addFeedback(feedback: FeedbackDto): Observable<string> {
  //   return this.http.post<string>(`${this.getBasePath()}/recyclingCenter/recieveFeedback`, feedback);
  // }
  //
  // public getFeedbackForCenter(id: number): Observable<FeedbackDto[]> {
  //   return this.http.get<FeedbackDto[]>(`${this.getBasePath()}/recyclingCenter/getFeedback/${id}`)
  // }

  public getHeaders() {
      const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8')
      return headers;
  }

}
