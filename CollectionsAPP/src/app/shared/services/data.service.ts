import { Injectable } from '@angular/core';
import {JavaHttpService} from "../connection/http/java-http.service";
import {map, Observable} from "rxjs";
import {EndUserDto} from "../connection/models/end-user.dto";
import {SessionService} from "./session.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ResponseMessage, SentMessageDto} from "../connection/models/response-message";
import { ProductDtoRequest } from '../connection/models/ProductDtoRequest';
import { OrderDtoRequest } from '../connection/models/OrderDtoRequest';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  getCurrentUser(): Observable<EndUserDto> {
    return this.http.get<EndUserDto>('/user/self')
  }

  // setCurrentUser(redirect: boolean, completionHandler: Function = () => {}) {
  //   this.getCurrentUser().subscribe(
  //     {
  //       complete: () => { if(redirect) {this.router.navigate([".."],{relativeTo: this.route})}
  //                           completionHandler()},
  //       error: (error) => {console.log(error)},
  //       next: (response:EndUserDto) => {
  //         let categories:String[] = []
  //         response.collections.forEach( function(value) {
  //           categories.push(value.name)
  //         });
  //         localStorage.setItem("userName",response.username)
  //         localStorage.setItem("categories",categories.toString())
  //         SessionService.getInstance().currentUser = response
  //       }
  //     }
  //   )
  // }

  getContacts() {
    return this.http.get<EndUserDto[]>('/message/get-users')
  }

  sendMessage(id: number, message: string): Observable<ResponseMessage> {
    let sentMessage = new SentMessageDto(message)
    return this.http.post<ResponseMessage, SentMessageDto>('/message/send/receiver='+id, sentMessage)
  }

  // getCollection(collectionName: string): Observable<ItemCollectionDto> {
  //   console.log(collectionName)
  //   let path = '/collection/find/name='+collectionName
  //   console.log(path)
  //   return this.http.get<ItemCollectionDto>(path)
  // }

  getUserByName(userName: string): Observable<EndUserDto> {
    let path = '/user/find/username='+userName
    return this.http.get<EndUserDto>(path)
  }

  addOrder(order: OrderDtoRequest){
    let path = '/order/addOrder'
    return this.http.post<ResponseMessage,OrderDtoRequest>(path,order)
  }

  getAllProducts() {
    let path = '/product/get'
    return this.http.get<ProductDtoRequest[]>(path)
  }

  addProduct(product: ProductDtoRequest): Observable<ResponseMessage> {
    let path = '/product/save'
    return this.http.post<ResponseMessage,ProductDtoRequest>(path,product)
  }
}
