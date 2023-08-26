import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JavaHttpService } from 'src/app/shared/connection/http/java-http.service';
import { EndUserDto } from 'src/app/shared/connection/models/end-user.dto';
import { DataService } from 'src/app/shared/services/data.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  
  users: EndUserDto[] = []

  constructor(
    private dataService: DataService,
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.dataService.getUsers().subscribe({
      complete:() => {},
      error: (error) => {console.log(error)},
      next: (result: EndUserDto[]) => {
        this.users = result
      }
     })
  }

  saveUser(user: EndUserDto, i: number){

  }

  deleteUser(id: number) {
    this.dataService.deleteUser(id).subscribe({
      complete:() => {},
      error: (error) => {console.log(error)},
      next: () => {
        this.router.navigate([this.route.url]);
      }
     })
  }

}
