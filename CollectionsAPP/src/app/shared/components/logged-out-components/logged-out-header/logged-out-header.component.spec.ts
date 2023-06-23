import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoggedOutHeaderComponent } from './logged-out-header.component';

describe('LoggedOutHeaderComponent', () => {
  let component: LoggedOutHeaderComponent;
  let fixture: ComponentFixture<LoggedOutHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoggedOutHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoggedOutHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
