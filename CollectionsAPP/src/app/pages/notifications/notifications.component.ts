import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JavaHttpService } from 'src/app/shared/connection/http/java-http.service';
import { NotificationDto } from 'src/app/shared/connection/models/notification.dto';
import { DataService } from 'src/app/shared/services/data.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit{

  notifications : NotificationDto[]

  constructor(
    private dataService: DataService,
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void{
    this.dataService.setCurrentUser(false);
    if((localStorage.getItem("isLoggedIn") ?? "false") !== "true"){
      return;
    }
    this.dataService.getNotifications().subscribe({
      complete: () => {},
      error: (error) => {},
      next: (response:NotificationDto[]) => {
        this.notifications = response;
      }
    })
  }
  
}
