import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JavaHttpService } from 'src/app/shared/connection/http/java-http.service';
import { OrderDtoResponse } from 'src/app/shared/connection/models/OrderDtoResponse';
import { DataService } from 'src/app/shared/services/data.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orders: OrderDtoResponse[] = []

  constructor(
    private dataService: DataService,
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
       this.dataService.getOrders().subscribe({
        complete:() => {},
        error: (error) => {console.log(error)},
        next: (result: OrderDtoResponse[]) => {
          this.orders = result
        }
       })
  }
  
  updateStatus(id: number){
    this.dataService.updateStatus(id)
  }

  deleteOrder(id: number) {
    this.dataService.deleteOrder(id)
  }

  onClickItem(id: number) {
    this.router.navigate(['../item/details/'+id],{
      onSameUrlNavigation:"reload"
    })
  }
}