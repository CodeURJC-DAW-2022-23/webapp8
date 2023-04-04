import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserImageComponent } from './user-image.component';

describe('UserImageComponent', () => {
  let component: UserImageComponent;
  let fixture: ComponentFixture<UserImageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserImageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
