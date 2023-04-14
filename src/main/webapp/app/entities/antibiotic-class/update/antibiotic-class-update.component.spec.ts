import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AntibioticClassFormService } from './antibiotic-class-form.service';
import { AntibioticClassService } from '../service/antibiotic-class.service';
import { IAntibioticClass } from '../antibiotic-class.model';

import { AntibioticClassUpdateComponent } from './antibiotic-class-update.component';

describe('AntibioticClass Management Update Component', () => {
  let comp: AntibioticClassUpdateComponent;
  let fixture: ComponentFixture<AntibioticClassUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let antibioticClassFormService: AntibioticClassFormService;
  let antibioticClassService: AntibioticClassService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AntibioticClassUpdateComponent],
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
      .overrideTemplate(AntibioticClassUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AntibioticClassUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    antibioticClassFormService = TestBed.inject(AntibioticClassFormService);
    antibioticClassService = TestBed.inject(AntibioticClassService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const antibioticClass: IAntibioticClass = { id: 456 };

      activatedRoute.data = of({ antibioticClass });
      comp.ngOnInit();

      expect(comp.antibioticClass).toEqual(antibioticClass);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAntibioticClass>>();
      const antibioticClass = { id: 123 };
      jest.spyOn(antibioticClassFormService, 'getAntibioticClass').mockReturnValue(antibioticClass);
      jest.spyOn(antibioticClassService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ antibioticClass });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: antibioticClass }));
      saveSubject.complete();

      // THEN
      expect(antibioticClassFormService.getAntibioticClass).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(antibioticClassService.update).toHaveBeenCalledWith(expect.objectContaining(antibioticClass));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAntibioticClass>>();
      const antibioticClass = { id: 123 };
      jest.spyOn(antibioticClassFormService, 'getAntibioticClass').mockReturnValue({ id: null });
      jest.spyOn(antibioticClassService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ antibioticClass: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: antibioticClass }));
      saveSubject.complete();

      // THEN
      expect(antibioticClassFormService.getAntibioticClass).toHaveBeenCalled();
      expect(antibioticClassService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAntibioticClass>>();
      const antibioticClass = { id: 123 };
      jest.spyOn(antibioticClassService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ antibioticClass });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(antibioticClassService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
