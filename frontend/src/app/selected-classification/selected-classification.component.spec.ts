import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectedClassificationComponent } from './selected-classification.component';

describe('SelectedClassificationComponent', () => {
  let component: SelectedClassificationComponent;
  let fixture: ComponentFixture<SelectedClassificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectedClassificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectedClassificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
