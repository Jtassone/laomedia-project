import { Component, ChangeDetectorRef, NgZone } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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

  letUserContinue(): void {
    this.auth.continueUnregistered();
  }

  letUserLeave(): void {
    this.auth.logout();
  }

  isLoggedIn(): boolean {
    return this.auth.mayContinue();
  }

  constructor(
    private ref: ChangeDetectorRef,
    private ngZone: NgZone,
    private http: HttpService,
    private auth: AuthService,
    private router: Router,
  ) {}

  ngOnInit() {
    onAuthUIStateChange((authState, authData) => {
      this.authState = authState;
      this.user = authData as CognitoUserInterface;
      if (this.user) {
        this.auth.login(this.user.username);
        this.router.navigate(['/admin']);
      } else {
        this.auth.logout();
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
