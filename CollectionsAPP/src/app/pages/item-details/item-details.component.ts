import {Component, OnInit} from '@angular/core';
import {DataService} from "../../shared/services/data.service";
import {JavaHttpService} from "../../shared/connection/http/java-http.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ItemDto} from "../../shared/connection/models/item.dto";
import {ResponseMessage} from "../../shared/connection/models/response-message";
import {SessionService} from "../../shared/services/session.service";

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.css']
})
export class ItemDetailsComponent implements OnInit{
  public item: ItemDto;

  constructor(
    private dataService: DataService,
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    let id = Number(this.route.snapshot.paramMap.get('id'));
    this.dataService.getItemById(id).subscribe({
      complete:() => {},
      error: (error) => {console.log(error)},
      next: (result:ItemDto) => {
        console.log(result)
        this.item = result}
    })
  }

  onContactUser() {
    let message = "";
    console.log(message)
    this.dataService.sendMessage(SessionService.getInstance().currentUser.id, message).subscribe({
      complete: () => {
        this.router.navigate(['../chat'],{
          onSameUrlNavigation:"reload"
        })
      },
      error: (error) => {},
      next: (response:ResponseMessage) => {
      }
    })
  }
}
