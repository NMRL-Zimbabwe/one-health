import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../antibiotic.test-samples';

import { AntibioticFormService } from './antibiotic-form.service';

describe('Antibiotic Form Service', () => {
  let service: AntibioticFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AntibioticFormService);
  });

  describe('Service methods', () => {
    describe('createAntibioticFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAntibioticFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          })
        );
      });

      it('passing IAntibiotic should create a new form with FormGroup', () => {
        const formGroup = service.createAntibioticFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          })
        );
      });
    });

    describe('getAntibiotic', () => {
      it('should return NewAntibiotic for default Antibiotic initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAntibioticFormGroup(sampleWithNewData);

        const antibiotic = service.getAntibiotic(formGroup) as any;

        expect(antibiotic).toMatchObject(sampleWithNewData);
      });

      it('should return NewAntibiotic for empty Antibiotic initial value', () => {
        const formGroup = service.createAntibioticFormGroup();

        const antibiotic = service.getAntibiotic(formGroup) as any;

        expect(antibiotic).toMatchObject({});
      });

      it('should return IAntibiotic', () => {
        const formGroup = service.createAntibioticFormGroup(sampleWithRequiredData);

        const antibiotic = service.getAntibiotic(formGroup) as any;

        expect(antibiotic).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAntibiotic should not enable id FormControl', () => {
        const formGroup = service.createAntibioticFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAntibiotic should disable id FormControl', () => {
        const formGroup = service.createAntibioticFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
