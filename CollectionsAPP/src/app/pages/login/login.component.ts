import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthRequest} from "../../internal-models/auth-request";
import {ActivatedRoute, Router} from "@angular/router";
import {LoginResponseDto} from "../../shared/connection/models/login-response.dto";
import {SessionDetails} from "../../internal-models/session-details";
import { DataService } from 'src/app/shared/services/data.service';
import { AuthService } from 'src/app/shared/services/auth.service';
import { SessionService } from 'src/app/shared/services/session.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username = '';
  badCredentials = false;
  errorString = '';

  form = new FormGroup({
    username: new FormControl('',[Validators.required,Validators.minLength(5),Validators.maxLength(30)]),
    password: new FormControl('',[Validators.required])
  });
  message: string = "Do not have an account?";

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private dataService: DataService
  ){}

  onSubmit() {

    if(this.form.valid) {
      let username = this.form.controls.username.value ?? "";
      let password = this.form.controls.password.value ?? "";

      const loginRequest = new AuthRequest(username,password)
      console.log(loginRequest)
      this.authService.loginPostRequest(loginRequest)
        .subscribe({
            complete: () => {
              // this.dataService.setCurrentUser(true)
            },
            error: (error) => { this.message = error.error.message },
            next: (response : LoginResponseDto | String) => {
              let loginResponse = response as LoginResponseDto
              console.log(response)
              let currentSession = new SessionDetails(username,true, loginResponse.role == "Admin" ? true : false);
              SessionService.getInstance().saveUserData(currentSession)
              this.router.navigate([".."])
            }
          });
      }
    }
}
