import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavBarButtonComponent } from './nav-bar-button.component';

describe('NavBarButtonComponent', () => {
  let component: NavBarButtonComponent;
  let fixture: ComponentFixture<NavBarButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavBarButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavBarButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
