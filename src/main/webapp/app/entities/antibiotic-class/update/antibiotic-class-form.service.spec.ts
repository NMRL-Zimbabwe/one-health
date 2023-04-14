import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../antibiotic-class.test-samples';

import { AntibioticClassFormService } from './antibiotic-class-form.service';

describe('AntibioticClass Form Service', () => {
  let service: AntibioticClassFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AntibioticClassFormService);
  });

  describe('Service methods', () => {
    describe('createAntibioticClassFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAntibioticClassFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IAntibioticClass should create a new form with FormGroup', () => {
        const formGroup = service.createAntibioticClassFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getAntibioticClass', () => {
      it('should return NewAntibioticClass for default AntibioticClass initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAntibioticClassFormGroup(sampleWithNewData);

        const antibioticClass = service.getAntibioticClass(formGroup) as any;

        expect(antibioticClass).toMatchObject(sampleWithNewData);
      });

      it('should return NewAntibioticClass for empty AntibioticClass initial value', () => {
        const formGroup = service.createAntibioticClassFormGroup();

        const antibioticClass = service.getAntibioticClass(formGroup) as any;

        expect(antibioticClass).toMatchObject({});
      });

      it('should return IAntibioticClass', () => {
        const formGroup = service.createAntibioticClassFormGroup(sampleWithRequiredData);

        const antibioticClass = service.getAntibioticClass(formGroup) as any;

        expect(antibioticClass).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAntibioticClass should not enable id FormControl', () => {
        const formGroup = service.createAntibioticClassFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAntibioticClass should disable id FormControl', () => {
        const formGroup = service.createAntibioticClassFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
