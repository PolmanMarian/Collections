import {Component, OnInit} from '@angular/core';
import {JavaHttpService} from "../../shared/connection/http/java-http.service";
import {ActivatedRoute, Router} from "@angular/router";
import { ProductDtoRequest } from 'src/app/shared/connection/models/ProductDtoRequest';
import { DataService } from 'src/app/shared/services/data.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  products: ProductDtoRequest[] = [
    new ProductDtoRequest(1,'Product 1',10 , 100),
    new ProductDtoRequest(2,'Product 2',20 , 50),
    new ProductDtoRequest(3,'Product 3',30 , 75)
  ];

  constructor(
    private dataService: DataService,
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
       this.dataService.getAllProducts().subscribe({
        complete:() => {},
        error: (error) => {console.log(error)},
        next: (result: ProductDtoRequest[]) => {
          this.products = result
        }
       })
  }

  // getItemId(i: number): number {
  //     return (this.items.at(i) ?? SessionService.getInstance().getEmptyItemDto()).id ?? 0 ;
  // }

  // isLikedItem(i: number): boolean {
  //   if((localStorage.getItem("isLoggedIn") ?? "false") === "true"){
  //     return SessionService.getInstance().currentUser.likedItems.includes(this.getItemId(i));
  //   } else {
  //     return false
  //   }
  // }

  saveProduct(product: ProductDtoRequest, index: number){
    this.dataService.addProduct(product).subscribe({
      complete:() => {},
      error: (error) => {console.log(error)},
      next: () => {
        this.products[index] = product
      }
     })
  }

  onClickItem(id: number) {
    this.router.navigate(['../item/details/'+id],{
      onSameUrlNavigation:"reload"
    })
  }
}
