import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../analysis.test-samples';

import { AnalysisFormService } from './analysis-form.service';

describe('Analysis Form Service', () => {
  let service: AnalysisFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalysisFormService);
  });

  describe('Service methods', () => {
    describe('createAnalysisFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnalysisFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sampleId: expect.any(Object),
            analysisServiceId: expect.any(Object),
            result: expect.any(Object),
            dateResulted: expect.any(Object),
          })
        );
      });

      it('passing IAnalysis should create a new form with FormGroup', () => {
        const formGroup = service.createAnalysisFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sampleId: expect.any(Object),
            analysisServiceId: expect.any(Object),
            result: expect.any(Object),
            dateResulted: expect.any(Object),
          })
        );
      });
    });

    describe('getAnalysis', () => {
      it('should return NewAnalysis for default Analysis initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAnalysisFormGroup(sampleWithNewData);

        const analysis = service.getAnalysis(formGroup) as any;

        expect(analysis).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnalysis for empty Analysis initial value', () => {
        const formGroup = service.createAnalysisFormGroup();

        const analysis = service.getAnalysis(formGroup) as any;

        expect(analysis).toMatchObject({});
      });

      it('should return IAnalysis', () => {
        const formGroup = service.createAnalysisFormGroup(sampleWithRequiredData);

        const analysis = service.getAnalysis(formGroup) as any;

        expect(analysis).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnalysis should not enable id FormControl', () => {
        const formGroup = service.createAnalysisFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnalysis should disable id FormControl', () => {
        const formGroup = service.createAnalysisFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
