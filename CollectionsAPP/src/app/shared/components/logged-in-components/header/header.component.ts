import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { JavaHttpService } from 'src/app/shared/connection/http/java-http.service';
import { SessionService } from 'src/app/shared/services/session.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent{
  
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    private http: JavaHttpService,
  ) {  }

  logOut() {
    SessionService.getInstance().clearSession()
    window.location.reload()
    this.router.navigate([".."])
  }

  onHomeClick() {
    this.router.navigate([".."])
  }
}
