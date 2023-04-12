import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../analysis-service.test-samples';

import { AnalysisServiceFormService } from './analysis-service-form.service';

describe('AnalysisService Form Service', () => {
  let service: AnalysisServiceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalysisServiceFormService);
  });

  describe('Service methods', () => {
    describe('createAnalysisServiceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnalysisServiceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });

      it('passing IAnalysisService should create a new form with FormGroup', () => {
        const formGroup = service.createAnalysisServiceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });
    });

    describe('getAnalysisService', () => {
      it('should return NewAnalysisService for default AnalysisService initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAnalysisServiceFormGroup(sampleWithNewData);

        const analysisService = service.getAnalysisService(formGroup) as any;

        expect(analysisService).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnalysisService for empty AnalysisService initial value', () => {
        const formGroup = service.createAnalysisServiceFormGroup();

        const analysisService = service.getAnalysisService(formGroup) as any;

        expect(analysisService).toMatchObject({});
      });

      it('should return IAnalysisService', () => {
        const formGroup = service.createAnalysisServiceFormGroup(sampleWithRequiredData);

        const analysisService = service.getAnalysisService(formGroup) as any;

        expect(analysisService).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnalysisService should not enable id FormControl', () => {
        const formGroup = service.createAnalysisServiceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnalysisService should disable id FormControl', () => {
        const formGroup = service.createAnalysisServiceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
