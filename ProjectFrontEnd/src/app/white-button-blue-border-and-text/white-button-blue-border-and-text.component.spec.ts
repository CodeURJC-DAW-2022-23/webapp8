import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WhiteButtonBlueBorderAndTextComponent } from './white-button-blue-border-and-text.component';

describe('WhiteButtonBlueBorderAndTextComponent', () => {
  let component: WhiteButtonBlueBorderAndTextComponent;
  let fixture: ComponentFixture<WhiteButtonBlueBorderAndTextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WhiteButtonBlueBorderAndTextComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WhiteButtonBlueBorderAndTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
