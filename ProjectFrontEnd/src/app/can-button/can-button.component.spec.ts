import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CanButtonComponent } from './can-button.component';

describe('CanButtonComponent', () => {
  let component: CanButtonComponent;
  let fixture: ComponentFixture<CanButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CanButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CanButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
