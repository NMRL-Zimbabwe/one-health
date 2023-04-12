import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrgnanismFormService } from './orgnanism-form.service';
import { OrgnanismService } from '../service/orgnanism.service';
import { IOrgnanism } from '../orgnanism.model';

import { OrgnanismUpdateComponent } from './orgnanism-update.component';

describe('Orgnanism Management Update Component', () => {
  let comp: OrgnanismUpdateComponent;
  let fixture: ComponentFixture<OrgnanismUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orgnanismFormService: OrgnanismFormService;
  let orgnanismService: OrgnanismService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrgnanismUpdateComponent],
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
      .overrideTemplate(OrgnanismUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrgnanismUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orgnanismFormService = TestBed.inject(OrgnanismFormService);
    orgnanismService = TestBed.inject(OrgnanismService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const orgnanism: IOrgnanism = { id: 456 };

      activatedRoute.data = of({ orgnanism });
      comp.ngOnInit();

      expect(comp.orgnanism).toEqual(orgnanism);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrgnanism>>();
      const orgnanism = { id: 123 };
      jest.spyOn(orgnanismFormService, 'getOrgnanism').mockReturnValue(orgnanism);
      jest.spyOn(orgnanismService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orgnanism });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orgnanism }));
      saveSubject.complete();

      // THEN
      expect(orgnanismFormService.getOrgnanism).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(orgnanismService.update).toHaveBeenCalledWith(expect.objectContaining(orgnanism));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrgnanism>>();
      const orgnanism = { id: 123 };
      jest.spyOn(orgnanismFormService, 'getOrgnanism').mockReturnValue({ id: null });
      jest.spyOn(orgnanismService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orgnanism: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orgnanism }));
      saveSubject.complete();

      // THEN
      expect(orgnanismFormService.getOrgnanism).toHaveBeenCalled();
      expect(orgnanismService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrgnanism>>();
      const orgnanism = { id: 123 };
      jest.spyOn(orgnanismService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orgnanism });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orgnanismService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
