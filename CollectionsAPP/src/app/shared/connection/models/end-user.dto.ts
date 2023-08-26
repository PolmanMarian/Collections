import {RoleDto} from "./role.dto";

export class EndUserDto {

  id: number = 0;
  username: string = '';
  password: string = '';
  role: string = "";


  constructor(id: number, username: string, password: string , role: string) {

    this.id = id;
    this.username = username;
    this.password = this.password
    this.role = role;
  }

}
