
export class ProductOrderDtoRequest {
  productId: number = 0;
  quantity: number = 0;

  constructor(id: number, quantity: number) {
    this.productId = id
    this.quantity = quantity
  }
}
