import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  username: string = 'UnregisteredUser';
  adminList = [
    'rstlouis',
    'testing',
    'doodle',
    'dssdvv',
    'jtassone',
  ]

  login(user: string): boolean {
    this.username = user;
    return true;
  }

  logout(): void {
    this.username = 'UnregisteredUser';
  }

  isLoggedIn(): boolean {
    return this.username !== 'UnregisteredUser';
  }

  getUsername(): string {
    return this.username;
  }

  isAdmin(): boolean {
    return this.isLoggedIn();
    // for (let admin of this.adminList) {
    //   if (this.username === admin) {
    //     return true;
    //   }
    // }
    // return false;
  }

  isRegistered(): boolean {
    return this.username !== 'UnregisteredUser';
  }

  constructor() { }
}
