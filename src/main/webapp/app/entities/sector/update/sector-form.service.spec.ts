import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sector.test-samples';

import { SectorFormService } from './sector-form.service';

describe('Sector Form Service', () => {
  let service: SectorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SectorFormService);
  });

  describe('Service methods', () => {
    describe('createSectorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSectorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          })
        );
      });

      it('passing ISector should create a new form with FormGroup', () => {
        const formGroup = service.createSectorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          })
        );
      });
    });

    describe('getSector', () => {
      it('should return NewSector for default Sector initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSectorFormGroup(sampleWithNewData);

        const sector = service.getSector(formGroup) as any;

        expect(sector).toMatchObject(sampleWithNewData);
      });

      it('should return NewSector for empty Sector initial value', () => {
        const formGroup = service.createSectorFormGroup();

        const sector = service.getSector(formGroup) as any;

        expect(sector).toMatchObject({});
      });

      it('should return ISector', () => {
        const formGroup = service.createSectorFormGroup(sampleWithRequiredData);

        const sector = service.getSector(formGroup) as any;

        expect(sector).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISector should not enable id FormControl', () => {
        const formGroup = service.createSectorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSector should disable id FormControl', () => {
        const formGroup = service.createSectorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
