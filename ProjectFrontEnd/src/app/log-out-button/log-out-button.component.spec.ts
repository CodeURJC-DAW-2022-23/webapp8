import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogOutButtonComponent } from './log-out-button.component';

describe('LogOutButtonComponent', () => {
  let component: LogOutButtonComponent;
  let fixture: ComponentFixture<LogOutButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogOutButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LogOutButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
