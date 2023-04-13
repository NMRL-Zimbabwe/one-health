import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../client-phone.test-samples';

import { ClientPhoneFormService } from './client-phone-form.service';

describe('ClientPhone Form Service', () => {
  let service: ClientPhoneFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClientPhoneFormService);
  });

  describe('Service methods', () => {
    describe('createClientPhoneFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createClientPhoneFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            number: expect.any(Object),
            clientId: expect.any(Object),
          })
        );
      });

      it('passing IClientPhone should create a new form with FormGroup', () => {
        const formGroup = service.createClientPhoneFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            number: expect.any(Object),
            clientId: expect.any(Object),
          })
        );
      });
    });

    describe('getClientPhone', () => {
      it('should return NewClientPhone for default ClientPhone initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createClientPhoneFormGroup(sampleWithNewData);

        const clientPhone = service.getClientPhone(formGroup) as any;

        expect(clientPhone).toMatchObject(sampleWithNewData);
      });

      it('should return NewClientPhone for empty ClientPhone initial value', () => {
        const formGroup = service.createClientPhoneFormGroup();

        const clientPhone = service.getClientPhone(formGroup) as any;

        expect(clientPhone).toMatchObject({});
      });

      it('should return IClientPhone', () => {
        const formGroup = service.createClientPhoneFormGroup(sampleWithRequiredData);

        const clientPhone = service.getClientPhone(formGroup) as any;

        expect(clientPhone).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IClientPhone should not enable id FormControl', () => {
        const formGroup = service.createClientPhoneFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewClientPhone should disable id FormControl', () => {
        const formGroup = service.createClientPhoneFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
