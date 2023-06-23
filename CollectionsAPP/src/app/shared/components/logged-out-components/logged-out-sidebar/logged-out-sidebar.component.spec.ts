import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoggedOutSidebarComponent } from './logged-out-sidebar.component';

describe('LoggedOutSidebarComponent', () => {
  let component: LoggedOutSidebarComponent;
  let fixture: ComponentFixture<LoggedOutSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoggedOutSidebarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoggedOutSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
