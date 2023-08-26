import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { DataService } from 'src/app/shared/services/data.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  userName: string;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private dataService: DataService) {
  }

  categories: String[];

  ngOnInit(): void {
    // this.dataService.setCurrentUser(false, () =>{
    //   console.log(SessionService.getInstance().currentUser)

    //   let cachedUsername = localStorage.getItem("userName");
    //   this.userName = cachedUsername ? cachedUsername: "";

    //   let cachedCategories = localStorage.getItem("categories");
    //   this.categories = cachedCategories ? cachedCategories.split(","): [];
    // })
  }

  redirectToProducts() {
    this.router.navigate(['..'])
  }

  redirectToOrders() {
    this.router.navigate(['../orders'])
  }

  redirectToUsers() {
    this.router.navigate(['../users'])  
  }

  redirectToAddOrder() {
    this.router.navigate(['../add-order'])
  }

  redirectToAddProduct() {
    this.router.navigate(['../add-product'])
  }

  redirectToAddUser() {
    this.router.navigate(['../add-user'])  
  }

}
