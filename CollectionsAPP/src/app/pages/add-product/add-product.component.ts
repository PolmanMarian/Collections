import {Component, OnInit} from '@angular/core';
import {JavaHttpService} from "../../shared/connection/http/java-http.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import { ProductDtoRequest } from 'src/app/shared/connection/models/ProductDtoRequest';
import { DataService } from 'src/app/shared/services/data.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  message: string = ""
  form = new FormGroup({
    name: new FormControl('',[Validators.required,Validators.minLength(5),Validators.maxLength(30)]),
    price: new FormControl(0),
    suply: new FormControl(0)
  });
  private productID: number;

  constructor(
    private dataService: DataService,
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
  }

  onAddProduct() {
      let name = this.form.controls.name.value ?? ""
      let price = this.form.controls.price.value ?? 0
      let supply = this.form.controls.suply.value ?? 0

      let item = new ProductDtoRequest(0, name, price, supply)
      console.log(item);

      this.dataService.addProduct(item).subscribe({
        complete: () => {this.form.reset()},
        error: (error) => {console.log(error)},
        next: (result) => {console.log(result)}
      })
  }

}
