import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthRequest } from 'src/app/internal-models/auth-request';
import { JavaHttpService } from 'src/app/shared/connection/http/java-http.service';
import { OrderDtoRequest } from 'src/app/shared/connection/models/OrderDtoRequest';
import { ProductDtoRequest } from 'src/app/shared/connection/models/ProductDtoRequest';
import { ProductOrderDtoRequest } from 'src/app/shared/connection/models/ProductOrderDtoRequest';
import { ResponseMessage } from 'src/app/shared/connection/models/response-message';
import { AuthService } from 'src/app/shared/services/auth.service';
import { DataService } from 'src/app/shared/services/data.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {


  form = new FormGroup({
    username: new FormControl('',[Validators.required,Validators.minLength(5),Validators.maxLength(30)]),
    password: new FormControl('',[Validators.required]),
    confirmPassword: new FormControl('',[Validators.required])
  });

  constructor(
    private dataService: DataService,
    private http: JavaHttpService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
  }


  onAddUser() {

    // this.message = "Already have an account?";

    if(this.form.valid){
      let username = this.form.controls.username.value ?? "";
      let password = this.form.controls.password.value ?? "";
      let confirmPassword = this.form.controls.confirmPassword.value ?? "";

      if (password != confirmPassword){
        // this.message = "Missmatching passwords";
      } else {
        const registerRequest = new AuthRequest(username,password)
        this.authService.registerPostRequest(registerRequest).subscribe({
            complete: () => {
                this.router.navigate(["../users"],{relativeTo: this.route})
            },
            error: (error) => {
              console.log(error);
              // this.message = error.error.message
            },
            next: (response: ResponseMessage) => {
              // this.message = response.message;
              console.log(response.message)
            }
          }
          );
      }

    }

  }

}
