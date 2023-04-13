import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../client-address.test-samples';

import { ClientAddressFormService } from './client-address-form.service';

describe('ClientAddress Form Service', () => {
  let service: ClientAddressFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClientAddressFormService);
  });

  describe('Service methods', () => {
    describe('createClientAddressFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createClientAddressFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            clientId: expect.any(Object),
          })
        );
      });

      it('passing IClientAddress should create a new form with FormGroup', () => {
        const formGroup = service.createClientAddressFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            clientId: expect.any(Object),
          })
        );
      });
    });

    describe('getClientAddress', () => {
      it('should return NewClientAddress for default ClientAddress initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createClientAddressFormGroup(sampleWithNewData);

        const clientAddress = service.getClientAddress(formGroup) as any;

        expect(clientAddress).toMatchObject(sampleWithNewData);
      });

      it('should return NewClientAddress for empty ClientAddress initial value', () => {
        const formGroup = service.createClientAddressFormGroup();

        const clientAddress = service.getClientAddress(formGroup) as any;

        expect(clientAddress).toMatchObject({});
      });

      it('should return IClientAddress', () => {
        const formGroup = service.createClientAddressFormGroup(sampleWithRequiredData);

        const clientAddress = service.getClientAddress(formGroup) as any;

        expect(clientAddress).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IClientAddress should not enable id FormControl', () => {
        const formGroup = service.createClientAddressFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewClientAddress should disable id FormControl', () => {
        const formGroup = service.createClientAddressFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
