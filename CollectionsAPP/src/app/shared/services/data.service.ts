import { Injectable } from '@angular/core';
import {JavaHttpService} from "../connection/http/java-http.service";
import {ItemDto} from "../connection/models/item.dto";
import {map, Observable} from "rxjs";
import {EndUserDto} from "../connection/models/end-user.dto";
import {SessionService} from "./session.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ChatMessageDto} from "../connection/models/chat-message.dto";
import {ResponseMessage, SentMessageDto} from "../connection/models/response-message";
import { NotificationDto } from '../connection/models/notification.dto';
import { ItemCollectionDto } from '../connection/models/item-collection.dto';
import {Tag} from "../connection/models/tag";
import {ItemDtoRequest} from "../connection/models/itemDtoRequest";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  getAllItems(): Observable<ItemDto[]> {
    return this.http.get<ItemDto[]>('/item/all');
  }

  getFeed(): Observable<ItemDto[]> {
    return this.http.get<ItemDto[]>('/user/feed');
  }

  getCurrentUser(): Observable<EndUserDto> {
    return this.http.get<EndUserDto>('/user/self')
  }

  setCurrentUser(redirect: boolean, completionHandler: Function = () => {}) {
    this.getCurrentUser().subscribe(
      {
        complete: () => { if(redirect) {this.router.navigate([".."],{relativeTo: this.route})}
                            completionHandler()},
        error: (error) => {console.log(error)},
        next: (response:EndUserDto) => {
          let categories:String[] = []
          response.collections.forEach( function(value) {
            categories.push(value.name)
          });
          localStorage.setItem("userName",response.username)
          localStorage.setItem("categories",categories.toString())
          SessionService.getInstance().currentUser = response
        }
      }
    )
  }

  getContacts() {
    return this.http.get<EndUserDto[]>('/message/get-users')
  }

  getMessageWith(otherUser: number) {
    return this.http.get<ChatMessageDto[]>('/message/find/with='+otherUser)
  }

  sendMessage(id: number, message: string): Observable<ResponseMessage> {
    let sentMessage = new SentMessageDto(message)
    return this.http.post<ResponseMessage, SentMessageDto>('/message/send/receiver='+id, sentMessage)
  }

  getNotifications() {
    return this.http.get<NotificationDto[]>('/notification/find/limit=30');
  }

  getCollection(collectionName: string): Observable<ItemCollectionDto> {
    console.log(collectionName)
    let path = '/collection/find/name='+collectionName
    console.log(path)
    return this.http.get<ItemCollectionDto>(path)
  }
  getUserByName(userName: string): Observable<EndUserDto> {
    let path = '/user/find/username='+userName
    return this.http.get<EndUserDto>(path)
  }

  geLikedItems() {
    let path = '/user/liked-items'
    return this.http.get<ItemDto[]>(path)
  }

  getAllTags() {
    let path = '/tag/all'
    return this.http.get<Tag[]>(path)
  }

  getItemById(id: number) {
    let path = '/item/find/id='+id
    return this.http.get<ItemDto>(path)
  }

  getSearchItemsPath(type: string, name: string, tag: string) {
    let path:string ='/search';

    if(name==='ANY'){
      if(tag==="ANY"){
        path = '/' + type + "/search";
      }else{
        path = '/' + type + "/search?tag=" + tag;
      }
    } else {
      if(tag==="ANY"){
        path = '/' + type + "/search?name=" + name;
      }else{
        path = '/' + type + "/search?name=" + name + "&tag=" + tag;
      }
    }
    return path;
  }

  addItem(collectionId: number, item: ItemDtoRequest): Observable<ResponseMessage> {
    let path = '/item/save/collection='+collectionId
    return this.http.post<ResponseMessage,ItemDtoRequest>(path,item)
  }
}
