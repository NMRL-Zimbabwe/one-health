import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AnalysisServiceDetailComponent } from './analysis-service-detail.component';

describe('AnalysisService Management Detail Component', () => {
  let comp: AnalysisServiceDetailComponent;
  let fixture: ComponentFixture<AnalysisServiceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AnalysisServiceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ analysisService: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AnalysisServiceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnalysisServiceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load analysisService on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.analysisService).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
