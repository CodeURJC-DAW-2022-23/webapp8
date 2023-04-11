import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeftBarButtonComponent } from './left-bar-button.component';

describe('LeftBarButtonComponent', () => {
  let component: LeftBarButtonComponent;
  let fixture: ComponentFixture<LeftBarButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeftBarButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeftBarButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
