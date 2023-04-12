import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DemographicCodingFormService } from './demographic-coding-form.service';
import { DemographicCodingService } from '../service/demographic-coding.service';
import { IDemographicCoding } from '../demographic-coding.model';

import { DemographicCodingUpdateComponent } from './demographic-coding-update.component';

describe('DemographicCoding Management Update Component', () => {
  let comp: DemographicCodingUpdateComponent;
  let fixture: ComponentFixture<DemographicCodingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demographicCodingFormService: DemographicCodingFormService;
  let demographicCodingService: DemographicCodingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DemographicCodingUpdateComponent],
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
      .overrideTemplate(DemographicCodingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemographicCodingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demographicCodingFormService = TestBed.inject(DemographicCodingFormService);
    demographicCodingService = TestBed.inject(DemographicCodingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const demographicCoding: IDemographicCoding = { id: 456 };

      activatedRoute.data = of({ demographicCoding });
      comp.ngOnInit();

      expect(comp.demographicCoding).toEqual(demographicCoding);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemographicCoding>>();
      const demographicCoding = { id: 123 };
      jest.spyOn(demographicCodingFormService, 'getDemographicCoding').mockReturnValue(demographicCoding);
      jest.spyOn(demographicCodingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demographicCoding });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demographicCoding }));
      saveSubject.complete();

      // THEN
      expect(demographicCodingFormService.getDemographicCoding).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(demographicCodingService.update).toHaveBeenCalledWith(expect.objectContaining(demographicCoding));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemographicCoding>>();
      const demographicCoding = { id: 123 };
      jest.spyOn(demographicCodingFormService, 'getDemographicCoding').mockReturnValue({ id: null });
      jest.spyOn(demographicCodingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demographicCoding: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demographicCoding }));
      saveSubject.complete();

      // THEN
      expect(demographicCodingFormService.getDemographicCoding).toHaveBeenCalled();
      expect(demographicCodingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemographicCoding>>();
      const demographicCoding = { id: 123 };
      jest.spyOn(demographicCodingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demographicCoding });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demographicCodingService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
