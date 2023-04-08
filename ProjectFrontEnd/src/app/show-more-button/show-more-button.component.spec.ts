import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowMoreButtonComponent } from './show-more-button.component';

describe('ShowMoreButtonComponent', () => {
  let component: ShowMoreButtonComponent;
  let fixture: ComponentFixture<ShowMoreButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowMoreButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowMoreButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
