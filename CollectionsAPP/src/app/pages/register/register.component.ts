import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../shared/services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthRequest} from "../../internal-models/auth-request";
import {ResponseMessage} from "../../shared/connection/models/response-message";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  form = new FormGroup({
    username: new FormControl('',[Validators.required,Validators.minLength(5),Validators.maxLength(30)]),
    password: new FormControl('',[Validators.required]),
    confirmPassword: new FormControl('',[Validators.required])
  });
  message: string = "Already have an account?";

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ){}

  onSubmit() {

    this.message = "Already have an account?";

    if(this.form.valid){
      let username = this.form.controls.username.value ?? "";
      let password = this.form.controls.password.value ?? "";
      let confirmPassword = this.form.controls.confirmPassword.value ?? "";

      console.log(password)
      console.log(confirmPassword)

      if (password != confirmPassword){
        this.message = "Missmatching passwords";
      } else {
        const registerRequest = new AuthRequest(username,password)
        this.authService.registerPostRequest(registerRequest).subscribe({
            complete: () => {
                this.router.navigate(["../login"],{relativeTo: this.route})
            },
            error: (error) => {
              console.log(error);
              this.message = error.error.message
            },
            next: (response: ResponseMessage) => {
              this.message = response.message;
              console.log(response.message)
            }
          }
          );
      }

    }

  }
}
