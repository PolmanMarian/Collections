export class ResponseMessage {

  message:string='';

  constructor(message: string) {
    this.message = message;
  }
}

export class SentMessageDto {

  message: string = '';

  constructor(message: string) {
    this.message = message
  }

}
