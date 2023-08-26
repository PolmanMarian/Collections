import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JavaHttpService } from 'src/app/shared/connection/http/java-http.service';
import { OrderDtoRequest } from 'src/app/shared/connection/models/OrderDtoRequest';
import { ProductDtoRequest } from 'src/app/shared/connection/models/ProductDtoRequest';
import { ProductOrderDtoRequest } from 'src/app/shared/connection/models/ProductOrderDtoRequest';
import { DataService } from 'src/app/shared/services/data.service';

@Component({
  selector: 'app-add-order',
  templateUrl: './add-order.component.html',
  styleUrls: ['./add-order.component.css']
})
export class AddOrderComponent implements OnInit {

  products: ProductDtoRequest[]
  price: number = 0
  addedProducts: { product: ProductDtoRequest; quantity: number }[] = []

  form = new FormGroup({
    product: new FormControl(),
    productQuantity: new FormControl(0)
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
    let products = this.dataService.getAllProducts().subscribe({
      complete: () => {},
      error: (error) => {console.log(error)},
      next: (result: ProductDtoRequest[]) => {this.products = result}
    })
  }

  onAddProduct(event: Event) {
    event.preventDefault();
    let selectedProduct = this.form.controls.product.value
    let quantity = this.form.controls.productQuantity.value ?? 0
    this.addedProducts.push({product:selectedProduct, quantity:quantity});
    this.updatePrice()
  }

  onRemoveProduct(index: number) {
    this.addedProducts.splice(index,1);
    this.updatePrice()
  }

  updatePrice() {
    this.price = 0
    this.addedProducts.forEach(entry => {
      this.price = entry.product.price * entry.quantity
    });
  }

  onAddOrder() {
      let orderProducts: ProductOrderDtoRequest[] = []

      this.addedProducts.forEach(entry => {
        orderProducts.push(new ProductOrderDtoRequest(entry.product.id, entry.quantity))
      })

      this.dataService.addOrder(new OrderDtoRequest(orderProducts)).subscribe({
        complete: () => {this.form.reset()},
        error: (error) => {console.log(error)},
        next: (result) => {console.log(result)}
      })
  }

}
