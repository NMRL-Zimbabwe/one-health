import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AntibioticFormService } from './antibiotic-form.service';
import { AntibioticService } from '../service/antibiotic.service';
import { IAntibiotic } from '../antibiotic.model';

import { AntibioticUpdateComponent } from './antibiotic-update.component';

describe('Antibiotic Management Update Component', () => {
  let comp: AntibioticUpdateComponent;
  let fixture: ComponentFixture<AntibioticUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let antibioticFormService: AntibioticFormService;
  let antibioticService: AntibioticService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AntibioticUpdateComponent],
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
      .overrideTemplate(AntibioticUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AntibioticUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    antibioticFormService = TestBed.inject(AntibioticFormService);
    antibioticService = TestBed.inject(AntibioticService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const antibiotic: IAntibiotic = { id: 456 };

      activatedRoute.data = of({ antibiotic });
      comp.ngOnInit();

      expect(comp.antibiotic).toEqual(antibiotic);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAntibiotic>>();
      const antibiotic = { id: 123 };
      jest.spyOn(antibioticFormService, 'getAntibiotic').mockReturnValue(antibiotic);
      jest.spyOn(antibioticService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ antibiotic });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: antibiotic }));
      saveSubject.complete();

      // THEN
      expect(antibioticFormService.getAntibiotic).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(antibioticService.update).toHaveBeenCalledWith(expect.objectContaining(antibiotic));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAntibiotic>>();
      const antibiotic = { id: 123 };
      jest.spyOn(antibioticFormService, 'getAntibiotic').mockReturnValue({ id: null });
      jest.spyOn(antibioticService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ antibiotic: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: antibiotic }));
      saveSubject.complete();

      // THEN
      expect(antibioticFormService.getAntibiotic).toHaveBeenCalled();
      expect(antibioticService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAntibiotic>>();
      const antibiotic = { id: 123 };
      jest.spyOn(antibioticService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ antibiotic });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(antibioticService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
