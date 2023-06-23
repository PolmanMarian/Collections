import {Component, OnInit} from '@angular/core';
import {ItemDto} from "../../shared/connection/models/item.dto";
import {DataService} from "../../shared/services/data.service";
import {SessionService} from "../../shared/services/session.service";
import {JavaHttpService} from "../../shared/connection/http/java-http.service";
import {ResponseMessage} from "../../shared/connection/models/response-message";
import {ActivePerfRecorder} from "@angular/compiler-cli/src/ngtsc/perf";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  items: ItemDto[]

  constructor(
    private dataService: DataService,
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void{
      this.dataService.setCurrentUser(false);

      if((localStorage.getItem("isLoggedIn") ?? "false") === "true"){
        this.dataService.getFeed().subscribe({
          complete: () => {console.log("feed")},
          error: (error) => {
            console.log(error)
          },
          next: (response : ItemDto[]) => {
            this.items = response
            console.log(response)
          }
        });
      } else {
        this.dataService.getAllItems().subscribe({
          complete: () => {},
          error: (error) => {
            console.log(error)
          },
          next: (response : ItemDto[]) => {
            this.items = response
            console.log(response)
          }
        });
      }
  }

  print(tag: string) {
    console.log(tag)
  }

  getItemId(i: number): number {
      return (this.items.at(i) ?? SessionService.getInstance().getEmptyItemDto()).id ?? 0 ;
  }

  isLikedItem(i: number): boolean {
    if((localStorage.getItem("isLoggedIn") ?? "false") === "true"){
      return SessionService.getInstance().currentUser.likedItems.includes(this.getItemId(i));
    } else {
      return false
    }
  }

  unlike(i: number) {
    if((localStorage.getItem("isLoggedIn") ?? "false") === "true"){
    this.http.post<ResponseMessage,string>('/user/unlike-item/id='+this.getItemId(i),"").subscribe({
      complete:() => {window.location.reload()},
      error: (error) => {console.log(error)},
      next: (value) => {console.log(value)}
    });} else {
      this.router.navigate(["../login"],{relativeTo: this.route})
    }
  }

  like(i: number) {
    if((localStorage.getItem("isLoggedIn") ?? "false") === "true"){
    this.http.post<ResponseMessage,string>('/user/like-item/id='+this.getItemId(i),"").subscribe({
      complete:() => {window.location.reload()},
      error: (error) => {console.log(error)},
      next: (value) => {console.log(value)}
    });} else {
      this.router.navigate(["../login"],{relativeTo: this.route});
    }
  }

  onClickItem(id: number) {
    this.router.navigate(['../item/details/'+id],{
      onSameUrlNavigation:"reload"
    })
  }
}
