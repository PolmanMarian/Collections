import { Component } from '@angular/core';
import {ItemCollectionDto} from "../../shared/connection/models/item-collection.dto";
import {DataService} from "../../shared/services/data.service";
import {JavaHttpService} from "../../shared/connection/http/java-http.service";
import {ActivatedRoute, Router} from "@angular/router";
import {EndUserDto} from "../../shared/connection/models/end-user.dto";
import {ResponseMessage} from "../../shared/connection/models/response-message";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent {

  user: EndUserDto

  constructor(
    private dataService: DataService,
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    let userName = String(this.route.snapshot.paramMap.get('userName'));
    this.dataService.setCurrentUser(false, () => {
      this.dataService.getUserByName(userName).subscribe({
        complete: () => {
          console.log("We got user")
        },
        error: (error) => {
          console.log(error)
        },
        next: (response: EndUserDto) => {
          this.user = response
          console.log(response)
        }
      })
    });

  }

  onClickCollectionName(category: string) {
    this.router.navigate(['../collection/'+category],{
      onSameUrlNavigation:"reload"
    })
  }

  onContactUser() {
    let message = "";
    console.log(message)
    this.dataService.sendMessage(this.user.id, message).subscribe({
        complete: () => {
          this.router.navigate(['../chat'],{
            onSameUrlNavigation:"reload"
          })
        },
        error: (error) => {},
        next: (response:ResponseMessage) => {
        }
      })

  }

  isCurrentUser() {
    if ((localStorage.getItem("isLoggedIn") ?? "false") === "true") {
        return (Number(localStorage.getItem("userId") ?? 0) == this.user.id)
    } else {
      return false
    }
  }
}
