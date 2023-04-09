import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonWithoutBackgroundWhiteBorderAndTextComponent } from './button-without-background-white-border-and-text.component';

describe('ButtonWithoutBackgroundWhiteBorderAndTextComponent', () => {
  let component: ButtonWithoutBackgroundWhiteBorderAndTextComponent;
  let fixture: ComponentFixture<ButtonWithoutBackgroundWhiteBorderAndTextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ButtonWithoutBackgroundWhiteBorderAndTextComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ButtonWithoutBackgroundWhiteBorderAndTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
