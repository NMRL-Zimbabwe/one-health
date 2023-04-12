import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SampleTypeDetailComponent } from './sample-type-detail.component';

describe('SampleType Management Detail Component', () => {
  let comp: SampleTypeDetailComponent;
  let fixture: ComponentFixture<SampleTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SampleTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sampleType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SampleTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SampleTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sampleType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sampleType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
