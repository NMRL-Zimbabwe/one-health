import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../organism.test-samples';

import { OrganismFormService } from './organism-form.service';

describe('Organism Form Service', () => {
  let service: OrganismFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganismFormService);
  });

  describe('Service methods', () => {
    describe('createOrganismFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrganismFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          })
        );
      });

      it('passing IOrganism should create a new form with FormGroup', () => {
        const formGroup = service.createOrganismFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          })
        );
      });
    });

    describe('getOrganism', () => {
      it('should return NewOrganism for default Organism initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOrganismFormGroup(sampleWithNewData);

        const organism = service.getOrganism(formGroup) as any;

        expect(organism).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrganism for empty Organism initial value', () => {
        const formGroup = service.createOrganismFormGroup();

        const organism = service.getOrganism(formGroup) as any;

        expect(organism).toMatchObject({});
      });

      it('should return IOrganism', () => {
        const formGroup = service.createOrganismFormGroup(sampleWithRequiredData);

        const organism = service.getOrganism(formGroup) as any;

        expect(organism).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrganism should not enable id FormControl', () => {
        const formGroup = service.createOrganismFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrganism should disable id FormControl', () => {
        const formGroup = service.createOrganismFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
