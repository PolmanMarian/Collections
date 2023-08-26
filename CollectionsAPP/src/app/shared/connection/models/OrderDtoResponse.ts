import { OrderProductDtoResponse } from "./OrderProductDtoResponse"

export class OrderDtoResponse {
  
    id: number
    status: Status
    date: Date
    price: number
    orderProducts: OrderProductDtoResponse[]
  
    constructor(id: number, status: Status, date: Date, price: number, products: OrderProductDtoResponse[]) {
      this.id = id
      this.status = status
      this.date = date
      this.price = price
      this.orderProducts = products
    }

  }
  
  export enum Status {
    new = 'NEW',
    preparing = 'PREPARING',
    done = 'DONE'
  }