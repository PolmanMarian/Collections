import {EndUserDto} from "./end-user.dto";
import {Tag} from "./tag";
import {ItemCollectionDto} from "./item-collection.dto";

export class ItemDto {
  id: number = 0
  name: string = '';
  description: string = '';
  status: Status = Status.notForSale;
  tags: Tag[] = [];


  constructor(id: number, name: string, description: string, status: Status, tags: Tag[]) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.status = status;
    this.tags = tags;
  }
}

export enum Status {
  forSale = 'FOR_SALE',
  notForSale = 'NOT_FOR_SALE',
}
