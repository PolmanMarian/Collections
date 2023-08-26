import { Injectable } from '@angular/core';
import {JavaHttpService} from "../connection/http/java-http.service";
import {map, Observable} from "rxjs";
import {EndUserDto} from "../connection/models/end-user.dto";
import {ActivatedRoute, Router} from "@angular/router";
import {ResponseMessage, SentMessageDto} from "../connection/models/response-message";
import { ProductDtoRequest } from '../connection/models/ProductDtoRequest';
import { OrderDtoRequest } from '../connection/models/OrderDtoRequest';
import { OrderDtoResponse } from '../connection/models/OrderDtoResponse';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  getOrders() {
    let path = '/order/get'
    return this.http.get<OrderDtoResponse[]>(path)
  }

  getUsers() {
    let path = '/user/get'
    return this.http.get<EndUserDto[]>(path)
  }

  deleteUser(id: number){
    let path = `/user/delete/${id}`
    return this.http.delete<ResponseMessage>(path)
  }

  getUserByName(userName: string): Observable<EndUserDto> {
    let path = '/user/find/username='+userName
    return this.http.get<EndUserDto>(path)
  }

  addOrder(order: OrderDtoRequest){
    let path = '/order/addOrder'
    return this.http.post<ResponseMessage,OrderDtoRequest>(path,order)
  }

  updateStatus(id: number){
    let path = `/order/update-status/${id}`
    return this.http.get<ResponseMessage>(path)
  }

  deleteOrder(id: number){
    let path = `/order/delete/${id}`
    return this.http.delete<ResponseMessage>(path)
  }

  getAllProducts() {
    let path = '/product/get'
    return this.http.get<ProductDtoRequest[]>(path)
  }

  deleteProduct(id: number){
    let path = `/product/delete/${id}`
    return this.http.delete<ResponseMessage>(path)
  }

  addProduct(product: ProductDtoRequest): Observable<ResponseMessage> {
    let path = '/product/save'
    return this.http.post<ResponseMessage,ProductDtoRequest>(path,product)
  }
}
