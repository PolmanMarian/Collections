import {ItemCollectionDto} from "./item-collection.dto";
import {RoleDto} from "./role.dto";

export class EndUserDto {

  id: number = 0;
  username: string = '';
  roles: RoleDto[] = [];
  collections: ItemCollectionDto[] = [];
  likedItems: number[] = [];
  token: string = "";


  constructor(id: number, username: string, roles: RoleDto[], collections: ItemCollectionDto[], likedItems: number[], token: string) {
    this.id = id;
    this.username = username;
    this.roles = roles;
    this.collections = collections;
    this.likedItems = likedItems;
    this.token = token;
  }

}
