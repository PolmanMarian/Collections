import { ProductOrderDtoRequest } from "./ProductOrderDtoRequest"

export class OrderDtoRequest {
  
    productList: ProductOrderDtoRequest[]
  
    constructor(products: ProductOrderDtoRequest[]) {
      this.productList = products
    }

  }
  
  export enum Status {
    new = 'NEW',
    preparing = 'PREPARING',
    done = 'DONE'
  }