import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ClientPhoneFormService } from './client-phone-form.service';
import { ClientPhoneService } from '../service/client-phone.service';
import { IClientPhone } from '../client-phone.model';

import { ClientPhoneUpdateComponent } from './client-phone-update.component';

describe('ClientPhone Management Update Component', () => {
  let comp: ClientPhoneUpdateComponent;
  let fixture: ComponentFixture<ClientPhoneUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let clientPhoneFormService: ClientPhoneFormService;
  let clientPhoneService: ClientPhoneService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ClientPhoneUpdateComponent],
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
      .overrideTemplate(ClientPhoneUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientPhoneUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    clientPhoneFormService = TestBed.inject(ClientPhoneFormService);
    clientPhoneService = TestBed.inject(ClientPhoneService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const clientPhone: IClientPhone = { id: 456 };

      activatedRoute.data = of({ clientPhone });
      comp.ngOnInit();

      expect(comp.clientPhone).toEqual(clientPhone);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClientPhone>>();
      const clientPhone = { id: 123 };
      jest.spyOn(clientPhoneFormService, 'getClientPhone').mockReturnValue(clientPhone);
      jest.spyOn(clientPhoneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientPhone });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clientPhone }));
      saveSubject.complete();

      // THEN
      expect(clientPhoneFormService.getClientPhone).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(clientPhoneService.update).toHaveBeenCalledWith(expect.objectContaining(clientPhone));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClientPhone>>();
      const clientPhone = { id: 123 };
      jest.spyOn(clientPhoneFormService, 'getClientPhone').mockReturnValue({ id: null });
      jest.spyOn(clientPhoneService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientPhone: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clientPhone }));
      saveSubject.complete();

      // THEN
      expect(clientPhoneFormService.getClientPhone).toHaveBeenCalled();
      expect(clientPhoneService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClientPhone>>();
      const clientPhone = { id: 123 };
      jest.spyOn(clientPhoneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientPhone });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(clientPhoneService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
