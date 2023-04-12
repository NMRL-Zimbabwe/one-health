import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sample-type.test-samples';

import { SampleTypeFormService } from './sample-type-form.service';

describe('SampleType Form Service', () => {
  let service: SampleTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SampleTypeFormService);
  });

  describe('Service methods', () => {
    describe('createSampleTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSampleTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          })
        );
      });

      it('passing ISampleType should create a new form with FormGroup', () => {
        const formGroup = service.createSampleTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          })
        );
      });
    });

    describe('getSampleType', () => {
      it('should return NewSampleType for default SampleType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSampleTypeFormGroup(sampleWithNewData);

        const sampleType = service.getSampleType(formGroup) as any;

        expect(sampleType).toMatchObject(sampleWithNewData);
      });

      it('should return NewSampleType for empty SampleType initial value', () => {
        const formGroup = service.createSampleTypeFormGroup();

        const sampleType = service.getSampleType(formGroup) as any;

        expect(sampleType).toMatchObject({});
      });

      it('should return ISampleType', () => {
        const formGroup = service.createSampleTypeFormGroup(sampleWithRequiredData);

        const sampleType = service.getSampleType(formGroup) as any;

        expect(sampleType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISampleType should not enable id FormControl', () => {
        const formGroup = service.createSampleTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSampleType should disable id FormControl', () => {
        const formGroup = service.createSampleTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
