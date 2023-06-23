import {Component, OnInit} from '@angular/core';
import {DataService} from "../../shared/services/data.service";
import {JavaHttpService} from "../../shared/connection/http/java-http.service";
import {EndUserDto} from "../../shared/connection/models/end-user.dto";
import {ChatMessageDto} from "../../shared/connection/models/chat-message.dto";
import {ItemDto} from "../../shared/connection/models/item.dto";
import {SessionService} from "../../shared/services/session.service";
import {FormControl, FormGroup, FormRecord, Validators} from "@angular/forms";
import {ResponseMessage} from "../../shared/connection/models/response-message";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit{

  contacts: EndUserDto[];
  currentMessages: ChatMessageDto[] = [];
  otherUser: EndUserDto;
  activeUser: EndUserDto;
  form = new FormGroup({
    message: new FormControl('',[Validators.required])
  });

  constructor(
    private dataService: DataService,
    private http: JavaHttpService) {
  }

  ngOnInit(): void {
    this.dataService.setCurrentUser(false,() => {
      console.log("We got here")
      this.activeUser = SessionService.getInstance().currentUser
      console.log(this.activeUser)
      this.dataService.getContacts().subscribe({
        complete: () => {
          // console.log("Not here")
        },
        error: (error) => {
          console.log(error)
        },
        next: (response : EndUserDto[]) => {
          this.contacts = response
          console.log(this.contacts)
        }
      });
    })
  }

  reloadMessages(){
    this.dataService.getMessageWith(this.otherUser.id).subscribe({
      complete: () => {},
      error: (error) => {console.log(error)},
      next: (result:ChatMessageDto[]) => {
        console.log(result)
        this.currentMessages = result
      }
    })
  }

  onContactClick(index: number){
    this.otherUser = this.getOtherUser(index);
    this.reloadMessages();
  }

  getOtherUser(i: number): EndUserDto {
    return (this.contacts.at(i) ?? SessionService.getInstance().getEmptyUser());
  }

  onMessageSubmit() {
    if(this.otherUser){
      let message = this.form.controls.message.value ?? "";
      if(message === ""){
        return;
      }
      this.dataService.sendMessage(this.otherUser.id, message).subscribe({
        complete: () => {
          this.form.reset();
          this.reloadMessages();
        },
        error: (error) => {},
        next: (response:ResponseMessage) => {

        }
      })
    }
    this.form.reset()
  }
}
