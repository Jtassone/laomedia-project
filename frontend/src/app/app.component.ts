import { Component, ChangeDetectorRef, NgZone } from '@angular/core';
import { onAuthUIStateChange, CognitoUserInterface, AuthState } from '@aws-amplify/ui-components';

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
  }

  letUserLeave(): void {
    this.continueWithoutRegister = false;
  }

  constructor(private ref: ChangeDetectorRef, private ngZone: NgZone) {}

  ngOnInit() {
    onAuthUIStateChange((authState, authData) => {
      this.authState = authState;
      this.user = authData as CognitoUserInterface;
      this.ngZone.run(
        () => this.ref.detectChanges()
      );
    });
  }

  ngOnDestroy() {
    return onAuthUIStateChange;
  }
}
