import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PieChartBasicComponent } from './pie-chart-basic.component';

describe('PieChartBasicComponent', () => {
  let component: PieChartBasicComponent;
  let fixture: ComponentFixture<PieChartBasicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PieChartBasicComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PieChartBasicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
