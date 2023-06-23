import {EndUserDto} from "./end-user.dto";
import {Tag} from "./tag";
import {ItemCollectionDto} from "./item-collection.dto";
import {Status} from "./item.dto";

export class ItemDtoRequest {
  id: number = 0
  name: string = '';
  description: string = '';
  status: Status = Status.notForSale;
  tags: String[] = [];


  constructor(id: number, name: string, description: string, status: Status, tags: String[]) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.status = status;
    this.tags = tags;
  }
}
