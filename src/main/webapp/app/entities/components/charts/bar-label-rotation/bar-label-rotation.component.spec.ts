import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarLabelRotationComponent } from './bar-label-rotation.component';

describe('BarLabelRotationComponent', () => {
  let component: BarLabelRotationComponent;
  let fixture: ComponentFixture<BarLabelRotationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BarLabelRotationComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BarLabelRotationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
