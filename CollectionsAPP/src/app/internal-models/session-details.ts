
export class SessionDetails {

  username: string = '';
  isLoggedIn: boolean = false;
  isAdmin: boolean = false;

  constructor(username: string, isLoggedIn: boolean, isAdmin: boolean) {
    this.username = username;
    this.isLoggedIn = isLoggedIn;
    this.isAdmin = isAdmin;
  }
}
