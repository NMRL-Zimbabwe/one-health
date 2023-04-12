import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DemographyFormService } from './demography-form.service';
import { DemographyService } from '../service/demography.service';
import { IDemography } from '../demography.model';

import { DemographyUpdateComponent } from './demography-update.component';

describe('Demography Management Update Component', () => {
  let comp: DemographyUpdateComponent;
  let fixture: ComponentFixture<DemographyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demographyFormService: DemographyFormService;
  let demographyService: DemographyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DemographyUpdateComponent],
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
      .overrideTemplate(DemographyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemographyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demographyFormService = TestBed.inject(DemographyFormService);
    demographyService = TestBed.inject(DemographyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const demography: IDemography = { id: 456 };

      activatedRoute.data = of({ demography });
      comp.ngOnInit();

      expect(comp.demography).toEqual(demography);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemography>>();
      const demography = { id: 123 };
      jest.spyOn(demographyFormService, 'getDemography').mockReturnValue(demography);
      jest.spyOn(demographyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demography });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demography }));
      saveSubject.complete();

      // THEN
      expect(demographyFormService.getDemography).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(demographyService.update).toHaveBeenCalledWith(expect.objectContaining(demography));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemography>>();
      const demography = { id: 123 };
      jest.spyOn(demographyFormService, 'getDemography').mockReturnValue({ id: null });
      jest.spyOn(demographyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demography: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demography }));
      saveSubject.complete();

      // THEN
      expect(demographyFormService.getDemography).toHaveBeenCalled();
      expect(demographyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemography>>();
      const demography = { id: 123 };
      jest.spyOn(demographyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demography });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demographyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
