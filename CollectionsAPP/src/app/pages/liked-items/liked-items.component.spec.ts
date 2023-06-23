import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LikedItemsComponent } from './liked-items.component';

describe('LikedItemsComponent', () => {
  let component: LikedItemsComponent;
  let fixture: ComponentFixture<LikedItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LikedItemsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LikedItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
