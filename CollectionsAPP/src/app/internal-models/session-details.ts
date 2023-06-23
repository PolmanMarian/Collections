import {SessionService} from "../shared/services/session.service";

export class SessionDetails {

  username: string = '';
  authToken: string = '';
  isLoggedIn: boolean = false;
  isAdmin: boolean = false;

  constructor(username: string, authToken: string, isLoggedIn: boolean, isAdmin: boolean) {
    this.username = username;
    this.authToken = authToken;
    this.isLoggedIn = isLoggedIn;
    this.isAdmin = isAdmin;
  }
}
