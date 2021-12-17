import { Component, OnInit } from '@angular/core';
import { HttpService } from '../http.service';
import { UserEvent } from '../model/userEvent.model';
import { IUser } from '../model/user.model';

@Component({
  selector: 'lao-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  userState: 'loading' | 'ready' | 'submitting' | 'error';
  eventState: 'loading' | 'ready' | 'submitting' | 'error';
  registeredUsers: IUser[] = [];
  columnsToDisplay = ['user', 'event', 'verb'];
  selectedUser: string;
  newUser: string;
  events: UserEvent[];
  usersToDelete = Object.create({});
  adminColumns = ['search', 'username', 'confirmed', 'email', 'delete']

  selectUser(user: string): void {
    this.eventState = 'loading';
    this.newUser = user;
    this.http.getEvents(user).subscribe({
      next: data => {
        this.events = data;
        this.selectedUser = user;
        this.eventState = 'ready';
      }, error: err => {
        this.eventState = 'error';
      }
    })
  }

  deleteUser(user: string): void {
    this.usersToDelete[user] = true;
    this.http.deleteUser(user).subscribe({
      next: data => {
        delete this.usersToDelete[user];
        this.resetComponent();
      }, error: err => {
        delete this.usersToDelete[user];
      }
    })
  }

  getEmail(user: any): string {
    for (let att of user.attributes) {
      if (att.name === 'email') {
        return att.value;
      }
    }
    return '-- no email --';
  }

  resetComponent(): void {
    this.userState = 'loading';
    this.newUser = '';
    this.eventState = 'ready';
    this.events = [];
    this.selectedUser = '';
    this.http.getUsers().subscribe({
      next: data => {
        this.registeredUsers = data;
        this.userState = 'ready';
      }
    })
  }

  constructor(
    private http: HttpService,
  ) { }

  ngOnInit(): void {
    this.resetComponent();
  }

}
