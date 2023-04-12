import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AnalysisFormService } from './analysis-form.service';
import { AnalysisService } from '../service/analysis.service';
import { IAnalysis } from '../analysis.model';

import { AnalysisUpdateComponent } from './analysis-update.component';

describe('Analysis Management Update Component', () => {
  let comp: AnalysisUpdateComponent;
  let fixture: ComponentFixture<AnalysisUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let analysisFormService: AnalysisFormService;
  let analysisService: AnalysisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AnalysisUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AnalysisUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnalysisUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    analysisFormService = TestBed.inject(AnalysisFormService);
    analysisService = TestBed.inject(AnalysisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const analysis: IAnalysis = { id: 456 };

      activatedRoute.data = of({ analysis });
      comp.ngOnInit();

      expect(comp.analysis).toEqual(analysis);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalysis>>();
      const analysis = { id: 123 };
      jest.spyOn(analysisFormService, 'getAnalysis').mockReturnValue(analysis);
      jest.spyOn(analysisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analysis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: analysis }));
      saveSubject.complete();

      // THEN
      expect(analysisFormService.getAnalysis).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(analysisService.update).toHaveBeenCalledWith(expect.objectContaining(analysis));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalysis>>();
      const analysis = { id: 123 };
      jest.spyOn(analysisFormService, 'getAnalysis').mockReturnValue({ id: null });
      jest.spyOn(analysisService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analysis: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: analysis }));
      saveSubject.complete();

      // THEN
      expect(analysisFormService.getAnalysis).toHaveBeenCalled();
      expect(analysisService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalysis>>();
      const analysis = { id: 123 };
      jest.spyOn(analysisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analysis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(analysisService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
