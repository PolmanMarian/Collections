import { ProductDtoRequest } from "./ProductDtoRequest"

export class OrderProductDtoResponse {
  
    order: number
    product: ProductDtoRequest
    quantity: number

    constructor(order: number, product: ProductDtoRequest, quantity: number) {
      this.order = order
      this.product = product
      this.quantity = quantity
    }

  }