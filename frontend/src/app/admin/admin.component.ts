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

  registeredUsers: IUser[] = [];
  columnsToDisplay = ['user', 'event', 'verb'];
  selectedUser: string;
  events: UserEvent[];
  state: 'loading' | 'ready';
  usersToDelete = Object.create({});

  selectUser(user: string): void {
    this.http.getEvents(user).subscribe({
      next: data => {
        console.log(data);
        this.events = data;
        this.selectedUser = user;
      }
    })
  }

  deleteUser(user: string): void {
    this.usersToDelete[user] = true;
    this.http.deleteUser(user).subscribe({
      next: data => {
        console.log(data);
        delete this.usersToDelete[user];
        this.resetComponent();
      }
    })
  }

  resetComponent(): void {
    this.events = [];
    this.selectedUser = '';
    this.http.getUsers().subscribe({
      next: data => {
        console.log(`Users: ${JSON.stringify(data)}`);
        this.registeredUsers = data;
        this.state = 'ready';
      }
    })
    this.state = 'loading';
  }

  constructor(
    private http: HttpService,
  ) { }

  ngOnInit(): void {
    this.resetComponent();
  }

}
