import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  username: string = 'UnregisteredUser';
  continue: boolean = false;
  adminList = [
    'rstlouis',
    'testing',
    'doodle',
    'dssdvv',
    'jtassone',
  ]

  login(user: string): boolean {
    this.username = user;
    this.continue = true;
    return true;
  }

  logout(): void {
    this.continue = false;
    this.username = 'UnregisteredUser';
    this.router.navigateByUrl('/');
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

  continueUnregistered(): void {
    this.continue = true;
    this.username = 'UnregisteredUser';
  }

  mayContinue(): boolean {
    return this.continue;
  }

  isRegistered(): boolean {
    return this.username !== 'UnregisteredUser';
  }

  constructor(private router: Router) { }
}
