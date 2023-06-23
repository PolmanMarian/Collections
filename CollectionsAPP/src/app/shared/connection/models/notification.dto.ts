import {EndUserDto} from "./end-user.dto";
import {Tag} from "./tag";
import {ItemCollectionDto} from "./item-collection.dto";

export class NotificationDto {
  id: number = 0
  text: string = '';
  type: NotificationsType = NotificationsType.OTHER;
  date: Date = new Date();
  owner: number;
  from: number;
  item: number;
}

export enum NotificationsType {
  MESSAGE = 'MESSAGE',
  LIKE = 'LIKE',
  OTHER = 'OTHER'
}