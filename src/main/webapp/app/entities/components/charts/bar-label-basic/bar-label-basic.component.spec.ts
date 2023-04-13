import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarLabelBasicComponent } from './bar-label-basic.component';

describe('BarLabelBasicComponent', () => {
  let component: BarLabelBasicComponent;
  let fixture: ComponentFixture<BarLabelBasicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BarLabelBasicComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BarLabelBasicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
