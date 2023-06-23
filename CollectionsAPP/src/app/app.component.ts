import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {ItemDto} from "./shared/connection/models/item.dto";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent{


  // ngOnInit() {
  //   this.getItems();
  // }

  // public getItems(): void {
  //   this.itemService.getItems().subscribe(
  //     (response: ItemDto[]) => {
  //       this.items = response;
  //     },
  //     (error: HttpErrorResponse) => {
  //       alert(error.message)
  //     }
  //   )
  // }
}
