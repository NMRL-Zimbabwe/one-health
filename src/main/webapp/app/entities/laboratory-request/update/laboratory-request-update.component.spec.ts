import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LaboratoryRequestFormService } from './laboratory-request-form.service';
import { LaboratoryRequestService } from '../service/laboratory-request.service';
import { ILaboratoryRequest } from '../laboratory-request.model';

import { LaboratoryRequestUpdateComponent } from './laboratory-request-update.component';

describe('LaboratoryRequest Management Update Component', () => {
  let comp: LaboratoryRequestUpdateComponent;
  let fixture: ComponentFixture<LaboratoryRequestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let laboratoryRequestFormService: LaboratoryRequestFormService;
  let laboratoryRequestService: LaboratoryRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LaboratoryRequestUpdateComponent],
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
      .overrideTemplate(LaboratoryRequestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LaboratoryRequestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    laboratoryRequestFormService = TestBed.inject(LaboratoryRequestFormService);
    laboratoryRequestService = TestBed.inject(LaboratoryRequestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const laboratoryRequest: ILaboratoryRequest = { id: 456 };

      activatedRoute.data = of({ laboratoryRequest });
      comp.ngOnInit();

      expect(comp.laboratoryRequest).toEqual(laboratoryRequest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILaboratoryRequest>>();
      const laboratoryRequest = { id: 123 };
      jest.spyOn(laboratoryRequestFormService, 'getLaboratoryRequest').mockReturnValue(laboratoryRequest);
      jest.spyOn(laboratoryRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ laboratoryRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: laboratoryRequest }));
      saveSubject.complete();

      // THEN
      expect(laboratoryRequestFormService.getLaboratoryRequest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(laboratoryRequestService.update).toHaveBeenCalledWith(expect.objectContaining(laboratoryRequest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILaboratoryRequest>>();
      const laboratoryRequest = { id: 123 };
      jest.spyOn(laboratoryRequestFormService, 'getLaboratoryRequest').mockReturnValue({ id: null });
      jest.spyOn(laboratoryRequestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ laboratoryRequest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: laboratoryRequest }));
      saveSubject.complete();

      // THEN
      expect(laboratoryRequestFormService.getLaboratoryRequest).toHaveBeenCalled();
      expect(laboratoryRequestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILaboratoryRequest>>();
      const laboratoryRequest = { id: 123 };
      jest.spyOn(laboratoryRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ laboratoryRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(laboratoryRequestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
