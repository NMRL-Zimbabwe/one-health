import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../demography.test-samples';

import { DemographyFormService } from './demography-form.service';

describe('Demography Form Service', () => {
  let service: DemographyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DemographyFormService);
  });

  describe('Service methods', () => {
    describe('createDemographyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDemographyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordId: expect.any(Object),
            demographicCodingId: expect.any(Object),
            value: expect.any(Object),
          })
        );
      });

      it('passing IDemography should create a new form with FormGroup', () => {
        const formGroup = service.createDemographyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordId: expect.any(Object),
            demographicCodingId: expect.any(Object),
            value: expect.any(Object),
          })
        );
      });
    });

    describe('getDemography', () => {
      it('should return NewDemography for default Demography initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDemographyFormGroup(sampleWithNewData);

        const demography = service.getDemography(formGroup) as any;

        expect(demography).toMatchObject(sampleWithNewData);
      });

      it('should return NewDemography for empty Demography initial value', () => {
        const formGroup = service.createDemographyFormGroup();

        const demography = service.getDemography(formGroup) as any;

        expect(demography).toMatchObject({});
      });

      it('should return IDemography', () => {
        const formGroup = service.createDemographyFormGroup(sampleWithRequiredData);

        const demography = service.getDemography(formGroup) as any;

        expect(demography).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDemography should not enable id FormControl', () => {
        const formGroup = service.createDemographyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDemography should disable id FormControl', () => {
        const formGroup = service.createDemographyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
