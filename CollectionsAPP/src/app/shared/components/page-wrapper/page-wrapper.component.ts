import {Component, OnInit} from '@angular/core';
import {SessionDetails} from "../../../internal-models/session-details";
import {SessionService} from "../../services/session.service";

@Component({
  selector: 'app-page-wrapper',
  templateUrl: './page-wrapper.component.html',
  styleUrls: ['./page-wrapper.component.css']
})
export class PageWrapperComponent implements  OnInit{

  currentSession: SessionDetails;

  ngOnInit(): void {
    this.currentSession = SessionService.getInstance().getCurrentSession();
  }

}
