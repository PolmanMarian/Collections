import {EndUserDto} from "./end-user.dto";

export class ChatMessageDto {
  id: number = 0;
  message: string = " " ;
  sentDate: Date = new Date();
  sender: number;
  receiver: number;
}
