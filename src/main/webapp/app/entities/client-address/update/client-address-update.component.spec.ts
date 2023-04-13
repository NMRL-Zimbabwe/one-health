import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ClientAddressFormService } from './client-address-form.service';
import { ClientAddressService } from '../service/client-address.service';
import { IClientAddress } from '../client-address.model';

import { ClientAddressUpdateComponent } from './client-address-update.component';

describe('ClientAddress Management Update Component', () => {
  let comp: ClientAddressUpdateComponent;
  let fixture: ComponentFixture<ClientAddressUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let clientAddressFormService: ClientAddressFormService;
  let clientAddressService: ClientAddressService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ClientAddressUpdateComponent],
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
      .overrideTemplate(ClientAddressUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientAddressUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    clientAddressFormService = TestBed.inject(ClientAddressFormService);
    clientAddressService = TestBed.inject(ClientAddressService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const clientAddress: IClientAddress = { id: 456 };

      activatedRoute.data = of({ clientAddress });
      comp.ngOnInit();

      expect(comp.clientAddress).toEqual(clientAddress);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClientAddress>>();
      const clientAddress = { id: 123 };
      jest.spyOn(clientAddressFormService, 'getClientAddress').mockReturnValue(clientAddress);
      jest.spyOn(clientAddressService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientAddress });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clientAddress }));
      saveSubject.complete();

      // THEN
      expect(clientAddressFormService.getClientAddress).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(clientAddressService.update).toHaveBeenCalledWith(expect.objectContaining(clientAddress));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClientAddress>>();
      const clientAddress = { id: 123 };
      jest.spyOn(clientAddressFormService, 'getClientAddress').mockReturnValue({ id: null });
      jest.spyOn(clientAddressService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientAddress: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clientAddress }));
      saveSubject.complete();

      // THEN
      expect(clientAddressFormService.getClientAddress).toHaveBeenCalled();
      expect(clientAddressService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClientAddress>>();
      const clientAddress = { id: 123 };
      jest.spyOn(clientAddressService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientAddress });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(clientAddressService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
