import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AnalysisDetailComponent } from './analysis-detail.component';

describe('Analysis Management Detail Component', () => {
  let comp: AnalysisDetailComponent;
  let fixture: ComponentFixture<AnalysisDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AnalysisDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ analysis: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AnalysisDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnalysisDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load analysis on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.analysis).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
