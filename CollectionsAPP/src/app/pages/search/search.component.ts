import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JavaHttpService } from 'src/app/shared/connection/http/java-http.service';
import { ItemDto } from 'src/app/shared/connection/models/item.dto';
import { ResponseMessage } from 'src/app/shared/connection/models/response-message';
import { DataService } from 'src/app/shared/services/data.service';
import { SessionService } from 'src/app/shared/services/session.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {
  items: ItemDto[]
  type: string
  name: string
  tag: string
  
  constructor(
    private dataService: DataService,
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void{
    this.dataService.setCurrentUser(false);

    this.type = String(this.route.snapshot.paramMap.get('type'));
    this.name = String(this.route.snapshot.paramMap.get('name'));
    this.tag = String(this.route.snapshot.paramMap.get('tag'));
    
    let path = this.dataService.getSearchItemsPath(this.type, this.name, this.tag);
    console.log("Path = " + path);
    this.http.get<ItemDto[]>(path).subscribe({
      complete: () =>{},
      error: (error) => {console.log(error)},
      next: (response: ItemDto[]) => {
        this.items = response;
        console.log(response)
      }
    })
  
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
