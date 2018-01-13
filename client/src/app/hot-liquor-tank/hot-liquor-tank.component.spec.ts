import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HotLiquorTankComponent } from './hot-liquor-tank.component';

describe('HotLiquorTankComponent', () => {
  let component: HotLiquorTankComponent;
  let fixture: ComponentFixture<HotLiquorTankComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HotLiquorTankComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HotLiquorTankComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
