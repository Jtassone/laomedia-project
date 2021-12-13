import { Component, ChangeDetectorRef, NgZone } from '@angular/core';
import { onAuthUIStateChange, CognitoUserInterface, AuthState } from '@aws-amplify/ui-components';
import { AuthService } from './auth.service';
import { HttpService } from './http.service';

@Component({
  selector: 'lao-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'laomedeia';
  user: CognitoUserInterface | undefined;
  authState: AuthState;
  continueWithoutRegister: boolean = false;

  letUserContinue(): void {
    this.continueWithoutRegister = true;
    this.http.username = 'UnregisteredUser';
    this.auth.logout();
  }

  letUserLeave(): void {
    this.continueWithoutRegister = false;
    this.auth.logout();
  }

  constructor(
    private ref: ChangeDetectorRef,
    private ngZone: NgZone,
    private http: HttpService,
    private auth: AuthService,
  ) {}

  ngOnInit() {
    onAuthUIStateChange((authState, authData) => {
      this.authState = authState;
      this.user = authData as CognitoUserInterface;
      if (this.user) {
        this.http.username = this.user.username;
        this.auth.login(this.user.username);
      }
      this.ngZone.run(
        () => this.ref.detectChanges()
      );
    });
  }

  ngOnDestroy() {
    return onAuthUIStateChange;
  }

  isAdmin() {
    return this.auth.isAdmin();
  }
}
