import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../laboratory-request.test-samples';

import { LaboratoryRequestFormService } from './laboratory-request-form.service';

describe('LaboratoryRequest Form Service', () => {
  let service: LaboratoryRequestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LaboratoryRequestFormService);
  });

  describe('Service methods', () => {
    describe('createLaboratoryRequestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLaboratoryRequestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sampleId: expect.any(Object),
            sampleTypeId: expect.any(Object),
            dateCollected: expect.any(Object),
            dateReceived: expect.any(Object),
            sampleCondition: expect.any(Object),
            clientId: expect.any(Object),
            priority: expect.any(Object),
            status: expect.any(Object),
            remarks: expect.any(Object),
            locationId: expect.any(Object),
            sectorId: expect.any(Object),
            districtId: expect.any(Object),
            procinceId: expect.any(Object),
          })
        );
      });

      it('passing ILaboratoryRequest should create a new form with FormGroup', () => {
        const formGroup = service.createLaboratoryRequestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sampleId: expect.any(Object),
            sampleTypeId: expect.any(Object),
            dateCollected: expect.any(Object),
            dateReceived: expect.any(Object),
            sampleCondition: expect.any(Object),
            clientId: expect.any(Object),
            priority: expect.any(Object),
            status: expect.any(Object),
            remarks: expect.any(Object),
            locationId: expect.any(Object),
            sectorId: expect.any(Object),
            districtId: expect.any(Object),
            procinceId: expect.any(Object),
          })
        );
      });
    });

    describe('getLaboratoryRequest', () => {
      it('should return NewLaboratoryRequest for default LaboratoryRequest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLaboratoryRequestFormGroup(sampleWithNewData);

        const laboratoryRequest = service.getLaboratoryRequest(formGroup) as any;

        expect(laboratoryRequest).toMatchObject(sampleWithNewData);
      });

      it('should return NewLaboratoryRequest for empty LaboratoryRequest initial value', () => {
        const formGroup = service.createLaboratoryRequestFormGroup();

        const laboratoryRequest = service.getLaboratoryRequest(formGroup) as any;

        expect(laboratoryRequest).toMatchObject({});
      });

      it('should return ILaboratoryRequest', () => {
        const formGroup = service.createLaboratoryRequestFormGroup(sampleWithRequiredData);

        const laboratoryRequest = service.getLaboratoryRequest(formGroup) as any;

        expect(laboratoryRequest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILaboratoryRequest should not enable id FormControl', () => {
        const formGroup = service.createLaboratoryRequestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLaboratoryRequest should disable id FormControl', () => {
        const formGroup = service.createLaboratoryRequestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
