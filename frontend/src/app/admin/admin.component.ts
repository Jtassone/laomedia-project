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
  columnsToDisplay = ['user', 'event'];
  selectedUser: string;
  events: UserEvent[];

  selectUser(user: string): void {
    this.http.getEvents(user);
  }

  resetComponent(): void {
    this.events = [];
    this.selectedUser = 'rstlouis';
    this.http.getUsers().subscribe({
      next: data => {
        console.log(`Users: ${JSON.stringify(data)}`);
        this.registeredUsers = data;
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
