import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SampleTypeFormService } from './sample-type-form.service';
import { SampleTypeService } from '../service/sample-type.service';
import { ISampleType } from '../sample-type.model';

import { SampleTypeUpdateComponent } from './sample-type-update.component';

describe('SampleType Management Update Component', () => {
  let comp: SampleTypeUpdateComponent;
  let fixture: ComponentFixture<SampleTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sampleTypeFormService: SampleTypeFormService;
  let sampleTypeService: SampleTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SampleTypeUpdateComponent],
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
      .overrideTemplate(SampleTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SampleTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sampleTypeFormService = TestBed.inject(SampleTypeFormService);
    sampleTypeService = TestBed.inject(SampleTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sampleType: ISampleType = { id: 456 };

      activatedRoute.data = of({ sampleType });
      comp.ngOnInit();

      expect(comp.sampleType).toEqual(sampleType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISampleType>>();
      const sampleType = { id: 123 };
      jest.spyOn(sampleTypeFormService, 'getSampleType').mockReturnValue(sampleType);
      jest.spyOn(sampleTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sampleType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sampleType }));
      saveSubject.complete();

      // THEN
      expect(sampleTypeFormService.getSampleType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sampleTypeService.update).toHaveBeenCalledWith(expect.objectContaining(sampleType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISampleType>>();
      const sampleType = { id: 123 };
      jest.spyOn(sampleTypeFormService, 'getSampleType').mockReturnValue({ id: null });
      jest.spyOn(sampleTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sampleType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sampleType }));
      saveSubject.complete();

      // THEN
      expect(sampleTypeFormService.getSampleType).toHaveBeenCalled();
      expect(sampleTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISampleType>>();
      const sampleType = { id: 123 };
      jest.spyOn(sampleTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sampleType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sampleTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
