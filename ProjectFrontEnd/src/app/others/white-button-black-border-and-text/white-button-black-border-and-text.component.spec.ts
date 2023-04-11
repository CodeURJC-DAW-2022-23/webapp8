import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WhiteButtonBlackBorderAndTextComponent } from './white-button-black-border-and-text.component';

describe('WhiteButtonBlackBorderAndTextComponent', () => {
  let component: WhiteButtonBlackBorderAndTextComponent;
  let fixture: ComponentFixture<WhiteButtonBlackBorderAndTextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WhiteButtonBlackBorderAndTextComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WhiteButtonBlackBorderAndTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
