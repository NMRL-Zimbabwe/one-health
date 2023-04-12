import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AnalysisServiceFormService } from './analysis-service-form.service';
import { AnalysisServiceService } from '../service/analysis-service.service';
import { IAnalysisService } from '../analysis-service.model';

import { AnalysisServiceUpdateComponent } from './analysis-service-update.component';

describe('AnalysisService Management Update Component', () => {
  let comp: AnalysisServiceUpdateComponent;
  let fixture: ComponentFixture<AnalysisServiceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let analysisServiceFormService: AnalysisServiceFormService;
  let analysisServiceService: AnalysisServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AnalysisServiceUpdateComponent],
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
      .overrideTemplate(AnalysisServiceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnalysisServiceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    analysisServiceFormService = TestBed.inject(AnalysisServiceFormService);
    analysisServiceService = TestBed.inject(AnalysisServiceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const analysisService: IAnalysisService = { id: 456 };

      activatedRoute.data = of({ analysisService });
      comp.ngOnInit();

      expect(comp.analysisService).toEqual(analysisService);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalysisService>>();
      const analysisService = { id: 123 };
      jest.spyOn(analysisServiceFormService, 'getAnalysisService').mockReturnValue(analysisService);
      jest.spyOn(analysisServiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analysisService });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: analysisService }));
      saveSubject.complete();

      // THEN
      expect(analysisServiceFormService.getAnalysisService).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(analysisServiceService.update).toHaveBeenCalledWith(expect.objectContaining(analysisService));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalysisService>>();
      const analysisService = { id: 123 };
      jest.spyOn(analysisServiceFormService, 'getAnalysisService').mockReturnValue({ id: null });
      jest.spyOn(analysisServiceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analysisService: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: analysisService }));
      saveSubject.complete();

      // THEN
      expect(analysisServiceFormService.getAnalysisService).toHaveBeenCalled();
      expect(analysisServiceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalysisService>>();
      const analysisService = { id: 123 };
      jest.spyOn(analysisServiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analysisService });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(analysisServiceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
