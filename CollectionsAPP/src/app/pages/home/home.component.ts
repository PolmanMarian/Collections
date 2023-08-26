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

  saveProduct(product: ProductDtoRequest, index: number){
    this.dataService.addProduct(product).subscribe({
      complete:() => {},
      error: (error) => {console.log(error)},
      next: () => {
        this.products[index] = product
      }
     })
  }

  deleteProduct(id: number){
    this.dataService.deleteProduct(id).subscribe({
      complete:() => {},
      error: (error) => {console.log(error)},
      next: () => {
        this.router.navigate([this.route.url]);
      }
     })
  }
  
  onClickItem(id: number) {
    this.router.navigate(['../item/details/'+id],{
      onSameUrlNavigation:"reload"
    })
  }
}
