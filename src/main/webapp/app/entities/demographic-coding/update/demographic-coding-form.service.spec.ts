import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../demographic-coding.test-samples';

import { DemographicCodingFormService } from './demographic-coding-form.service';

describe('DemographicCoding Form Service', () => {
  let service: DemographicCodingFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DemographicCodingFormService);
  });

  describe('Service methods', () => {
    describe('createDemographicCodingFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDemographicCodingFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IDemographicCoding should create a new form with FormGroup', () => {
        const formGroup = service.createDemographicCodingFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getDemographicCoding', () => {
      it('should return NewDemographicCoding for default DemographicCoding initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDemographicCodingFormGroup(sampleWithNewData);

        const demographicCoding = service.getDemographicCoding(formGroup) as any;

        expect(demographicCoding).toMatchObject(sampleWithNewData);
      });

      it('should return NewDemographicCoding for empty DemographicCoding initial value', () => {
        const formGroup = service.createDemographicCodingFormGroup();

        const demographicCoding = service.getDemographicCoding(formGroup) as any;

        expect(demographicCoding).toMatchObject({});
      });

      it('should return IDemographicCoding', () => {
        const formGroup = service.createDemographicCodingFormGroup(sampleWithRequiredData);

        const demographicCoding = service.getDemographicCoding(formGroup) as any;

        expect(demographicCoding).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDemographicCoding should not enable id FormControl', () => {
        const formGroup = service.createDemographicCodingFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDemographicCoding should disable id FormControl', () => {
        const formGroup = service.createDemographicCodingFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
