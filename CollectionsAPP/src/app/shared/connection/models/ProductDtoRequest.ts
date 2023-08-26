export class ProductDtoRequest {
  
  id: number
  name: string
  price: number
  supply: number

  constructor(id: number, name: string, price: number, supply: number) {
    this.id = id
    this.name = name
    this.price = price
    this.supply = supply
  }
  
}
